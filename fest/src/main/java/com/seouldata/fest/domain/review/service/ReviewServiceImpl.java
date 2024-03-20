package com.seouldata.fest.domain.review.service;

import com.seouldata.common.exception.BusinessException;
import com.seouldata.common.exception.ErrorCode;
import com.seouldata.fest.domain.review.dto.request.AddReviewReq;
import com.seouldata.fest.domain.fest.entity.Fest;
import com.seouldata.fest.domain.review.entity.Review;
import com.seouldata.fest.domain.fest.repository.FestRepository;
import com.seouldata.fest.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final FestRepository festRepository;

    @Override
    public Long addReview(Long memSeq, AddReviewReq addReviewReq) {
        Fest foundFest = festRepository.findByFestSeq(addReviewReq.getFestSeq())
                .orElseThrow(() -> new BusinessException(ErrorCode.FEST_NOT_FOUND));

        return reviewRepository.save(Review.builder()
                .memSeq(memSeq)
                .fest(foundFest)
                .point(addReviewReq.getPoint())
                .content(addReviewReq.getContent())
                .build())
                .getReviewSeq();
    }

}
