package com.seouldata.fest.domain.fest.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seouldata.fest.domain.fest.dto.request.FindFestByCriteriaReq;
import com.seouldata.fest.domain.fest.dto.response.GetFestByCriteriaResDto;
import com.seouldata.fest.domain.fest.entity.Codename;
import com.seouldata.fest.domain.fest.entity.Fest;
import com.seouldata.fest.domain.fest.entity.QFest;
import com.seouldata.fest.domain.heart.repository.HeartRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FestRepositoryCustomImpl implements FestRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QFest fest = QFest.fest;
    private final HeartRepository heartRepository;

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

}
