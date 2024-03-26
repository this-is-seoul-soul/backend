package com.seouldata.fest.domain.review.service;

import com.seouldata.common.exception.BusinessException;
import com.seouldata.common.exception.ErrorCode;
import com.seouldata.fest.domain.review.dto.request.AddReviewReq;
import com.seouldata.fest.domain.fest.entity.Fest;
import com.seouldata.fest.domain.review.dto.request.ModifyReviewReq;
import com.seouldata.fest.domain.review.entity.Image;
import com.seouldata.fest.domain.review.entity.Review;
import com.seouldata.fest.domain.fest.repository.FestRepository;
import com.seouldata.fest.domain.review.entity.Tag;
import com.seouldata.fest.domain.review.repository.ImageRepository;
import com.seouldata.fest.domain.review.repository.ReviewRepository;
import com.seouldata.fest.domain.review.repository.TagRepository;
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
    private final ImageRepository imageRepository;
    private final TagRepository tagRepository;

    @Override
    public Long addReview(Long memSeq, AddReviewReq addReviewReq) {
        Fest foundFest = festRepository.findByFestSeq(addReviewReq.getFestSeq())
                .orElseThrow(() -> new BusinessException(ErrorCode.FEST_NOT_FOUND));

        Review review = reviewRepository.save(Review.builder()
                        .memSeq(memSeq)
                        .fest(foundFest)
                        .point(addReviewReq.getPoint())
                        .content(addReviewReq.getContent())
                        .build());

        if (addReviewReq.getImgUrl() != null) {
            addReviewReq.getImgUrl().stream()
                    .map(image -> Image.builder()
                            .review(review)
                            .imgUrl(image)
                            .build())
                    .forEach(imageRepository::save);
        }

        if (addReviewReq.getTag() != null) {
            addReviewReq.getTag().stream()
                    .map(tag -> Tag.builder()
                            .memSeq(memSeq)
                            .review(review)
                            .tagNo(tag)
                            .build())
                    .forEach(tagRepository::save);
        }

        return review.getReviewSeq();
    }

    @Override
    public void modifyReview(Long memSeq, ModifyReviewReq modifyReviewReq) {

        Review review = reviewRepository.findByReviewSeq(modifyReviewReq.getReviewSeq())
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));

        review.modify(modifyReviewReq.getContent(), modifyReviewReq.getPoint());

        if (modifyReviewReq.getImgUrl() != null) {
            imageRepository.deleteAllByReviewSeq(modifyReviewReq.getReviewSeq());
            modifyReviewReq.getImgUrl().stream()
                    .map(image -> Image.builder()
                            .review(review)
                            .imgUrl(image)
                            .build())
                    .forEach(imageRepository::save);
        }

        if (modifyReviewReq.getTag() != null) {
            tagRepository.deleteAllByReviewSeq(modifyReviewReq.getReviewSeq());
            modifyReviewReq.getTag().stream()
                    .map(tag -> Tag.builder()
                            .memSeq(memSeq)
                            .review(review)
                            .tagNo(tag)
                            .build())
                    .forEach(tagRepository::save);
        }
    }

}
