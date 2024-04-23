package com.seouldata.fest.domain.fest.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seouldata.common.exception.BusinessException;
import com.seouldata.common.exception.ErrorCode;
import com.seouldata.fest.domain.fest.dto.request.FindByCodeReq;
import com.seouldata.fest.domain.fest.dto.request.FindFestByCriteriaReq;
import com.seouldata.fest.domain.fest.dto.response.GetFestByCriteriaResDto;
import com.seouldata.fest.domain.fest.entity.Codename;
import com.seouldata.fest.domain.fest.entity.Fest;
import com.seouldata.fest.domain.fest.entity.QFest;
import com.seouldata.fest.domain.fest.entity.SortOption;
import com.seouldata.fest.domain.heart.entity.QHeart;
import com.seouldata.fest.domain.heart.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FestRepositoryCustomImpl implements FestRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QFest fest = QFest.fest;
    private final QHeart heart = QHeart.heart;
    private final HeartRepository heartRepository;

    private final String FREE = "무료";
    private final String ALL = "전체";

    @Override
    public List<GetFestByCriteriaResDto> findAllByCriteria(Long memSeq, FindFestByCriteriaReq findFestByCriteriaReq) {

        BooleanExpression criteriaBuilder = fest.isDeleted.isFalse();

        if (findFestByCriteriaReq.getYear() != null) {
            List<Integer> years = findFestByCriteriaReq.getYear();
            BooleanExpression yearCondition = null;

            for (Integer year : years) {
                BooleanExpression yearExp = fest.startDate.year().eq(year)
                        .or(fest.endDate.year().eq(year));

                if (yearCondition == null) {
                    yearCondition = yearExp;
                } else {
                    yearCondition = yearCondition.or(yearExp);
                }
            }

            criteriaBuilder = criteriaBuilder.and(yearCondition);
        }

        if (findFestByCriteriaReq.getCodename() != null) {
            List<String> codeNames = findFestByCriteriaReq.getCodename();
            BooleanExpression codeNameCondition = null;

            for (String codeName : codeNames) {
                int codeNum = Codename.getCodeNum(codeName);
                if (codeNum != -1) {
                    BooleanExpression codeNameExp = fest.codename.eq(codeNum);

                    if (codeNameCondition == null) {
                        codeNameCondition = codeNameExp;
                    } else {
                        codeNameCondition = codeNameCondition.or(codeNameExp);
                    }
                }
            }

            if (codeNameCondition != null) {
                criteriaBuilder = criteriaBuilder.and(codeNameCondition);
            }
        }

        double latitude = findFestByCriteriaReq.getLat();
        double longitude = findFestByCriteriaReq.getLot();
        double distanceInKm = findFestByCriteriaReq.getDistance();

        NumberExpression<Double> distanceExpression = Expressions.numberTemplate(Double.class,
                "st_distance_sphere(point(lat, lot), point({0}, {1}))", longitude, latitude);

        criteriaBuilder = criteriaBuilder.and(distanceExpression.loe(distanceInKm * 1000));

        List<GetFestByCriteriaResDto> result = queryFactory
                .select(Projections.constructor(GetFestByCriteriaResDto.class,
                        fest.festSeq,
                        fest.title,
                        fest.lot,
                        fest.lat))
                .from(fest)
                .where(criteriaBuilder)
                .fetch();

        for (GetFestByCriteriaResDto dto : result) {
            Fest f = queryFactory.selectFrom(fest)
                    .where(fest.festSeq.eq(dto.getFestSeq()))
                    .fetchOne();

            boolean heartExists = heartRepository.existsByFestAndAndMemSeq(f, memSeq);

            dto.setHeart(heartExists);
        }

        return result;
    }

    @Override
    public List<Fest> findByKeyword(String keyword) {

        List<Fest> result = queryFactory
                .selectFrom(fest)
                .leftJoin(heart).on(heart.fest.eq(fest))
                .where(fest.title.contains(keyword)
                        .and(fest.isDeleted.isFalse()))
                .groupBy(fest.festSeq)
                .orderBy(heart.count().desc())
                .limit(20)
                .fetch();

        return result;
    }

    @Override
    public List<Fest> findByKeywordAndLocation(String keyword, double lot, double lat) {

        NumberExpression<Double> distanceExpression = Expressions.numberTemplate(Double.class,
                "ST_DISTANCE_SPHERE(point(fest.lat, fest.lot), point(?1, ?2))", lat, lot);

        List<Fest> result = queryFactory
                .select(fest)
                .from(fest)
                .where(fest.title.contains(keyword)
                        .and(fest.isDeleted.isFalse()))
                .orderBy(distanceExpression.asc(), fest.festSeq.desc())
                .limit(20)
                .fetch();

        return result;
    }

    @Override
    public List<Fest> findByCodeWithCursor(FindByCodeReq findByCodeReq, int sort, Pageable pageable) {
        OrderSpecifier[] orderSpecifiers = createOrderOption(SortOption.getSortOption(sort));

        BooleanExpression predicate = fest.isDeleted.isFalse();

        int codeNum = Codename.getCodeNum(findByCodeReq.getCodename());
        if (codeNum > 0) {
            predicate = predicate.and(fest.codename.eq(codeNum));
        }
        if (findByCodeReq.isFree()) {
            predicate = predicate.and(fest.isFree.eq(FREE));
        }
        if (findByCodeReq.isContinue()) {
            predicate = predicate.and(fest.startDate.before(LocalDateTime.now())).and(fest.endDate.after(LocalDateTime.now()));
        }
        if (findByCodeReq.getRegion() != null && !findByCodeReq.getRegion().equals(ALL)) {
            predicate = predicate.and(fest.guname.eq(findByCodeReq.getRegion()));
        }

        return queryFactory.selectFrom(fest)
                .where(predicate)
                .orderBy(orderSpecifiers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private OrderSpecifier[] createOrderOption(SortOption sort) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        switch (sort) {
            case RECOMMEND:
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, fest.festSeq));
                break;
            case NEW:
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, fest.festSeq));
                break;
            default:
                throw new BusinessException(ErrorCode.SEARCH_OPTION_INVALID);
        }

        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }

}
