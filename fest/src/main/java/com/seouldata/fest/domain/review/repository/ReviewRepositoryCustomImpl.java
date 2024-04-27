package com.seouldata.fest.domain.review.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seouldata.common.exception.BusinessException;
import com.seouldata.common.exception.ErrorCode;
import com.seouldata.fest.domain.fest.entity.Fest;
import com.seouldata.fest.domain.fest.entity.SortOption;
import com.seouldata.fest.domain.review.entity.QReview;
import com.seouldata.fest.domain.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QReview review = QReview.review;


    @Override
    public List<Review> findAllWithCursor(Fest fest, int sort, Pageable pageable) {
        OrderSpecifier[] orderSpecifiers = createOrderOption(SortOption.getSortOption(sort));

        return queryFactory
                .selectFrom(review)
                .where(review.fest.eq(fest)
                        .and(review.isDeleted.eq(false)))
                .orderBy(orderSpecifiers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private OrderSpecifier[] createOrderOption(SortOption sort) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        switch (sort) {
            case RECOMMEND:
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, review.reviewSeq));
                break;
            case NEW:
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, review.reviewSeq));
                break;
            case LOW_POINT:
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, review.point));
                break;
            case HIGH_POINT:
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, review.point));
                break;
            default:
                throw new BusinessException(ErrorCode.SEARCH_OPTION_INVALID);
        }

        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }

}
