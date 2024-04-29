package com.seouldata.fest.domain.review.service;

import com.seouldata.common.exception.BusinessException;
import com.seouldata.common.exception.ErrorCode;
import com.seouldata.fest.domain.review.dto.response.TagResDto;
import com.seouldata.fest.domain.review.dto.request.AddReviewReq;
import com.seouldata.fest.domain.fest.entity.Fest;
import com.seouldata.fest.domain.review.dto.request.ModifyReviewReq;
import com.seouldata.fest.domain.review.dto.response.GetMemberInfoRes;
import com.seouldata.fest.domain.review.dto.response.GetReviewRes;
import com.seouldata.fest.domain.review.dto.response.GetReviewTotalRes;
import com.seouldata.fest.domain.review.dto.response.GetTagRes;
import com.seouldata.fest.domain.review.entity.Image;
import com.seouldata.fest.domain.review.entity.Review;
import com.seouldata.fest.domain.fest.repository.FestRepository;
import com.seouldata.fest.domain.review.entity.Tag;
import com.seouldata.fest.domain.review.repository.ImageRepository;
import com.seouldata.fest.domain.review.repository.ReviewRepository;
import com.seouldata.fest.domain.review.repository.TagRepository;
import com.seouldata.fest.domain.review.util.InfoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final FestRepository festRepository;
    private final ImageRepository imageRepository;
    private final TagRepository tagRepository;

    private final InfoUtil infoUtil;

    @Override
    public GetReviewTotalRes findReview(Long memSeq, Long festSeq, int sort, int page, int limit) {
        Fest fest = festRepository.findByFestSeq(festSeq)
                .orElseThrow(() -> new BusinessException(ErrorCode.FEST_NOT_FOUND));

        List<Review> reviewList = reviewRepository.findAllWithCursor(fest, sort, PageRequest.of(page, limit));

        return GetReviewTotalRes.builder()
                .reviewResList(reviewList.stream()
                        .map(review -> {
                            GetMemberInfoRes creatInfo = infoUtil.getMemberInfo(memSeq);

                            return GetReviewRes.builder()
                                    .reviewSeq(review.getReviewSeq())
                                    .content(review.getContent())
                                    .point(review.getPoint())
                                    .imgUrl(imageRepository.findImagesByReviewSeq(review.getReviewSeq())
                                            .stream()
                                            .map(image -> {
                                                return image.getImgUrl();
                                            }).collect(Collectors.toList()))
                                    .isMine((memSeq == review.getMemSeq()) ? true : false)
                                    .nickName(creatInfo.getNickname())
                                    .mbti(creatInfo.getMbti())
                                    .build();
                        })
                        .collect(Collectors.toList())
                )
                .nextCursor(reviewList.size() != 0 ? reviewList.get(reviewList.size() - 1).getReviewSeq() : 0)
                .nextPage(page + 1)
                .hasNext(reviewList.size() > limit ? true : false)
                .build();

    }

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

    @Override
    public void removeReview(Long memSeq, Long reviewSeq) {
        Review review = reviewRepository.findByReviewSeq(reviewSeq)
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));

        review.remove();

        List<Image> imgList = imageRepository.findImagesByReviewSeq(reviewSeq);

        if (imgList != null) {
            for (Image img : imgList) {
                img.remove();
            }
        }

        List<Tag> tagList = tagRepository.findTagsByMemSeqAndReviewSeq(memSeq, reviewSeq);

        if (tagList != null) {
            for (Tag tag : tagList) {
                tag.remove();
            }
        }
    }

    @Override
    public GetTagRes findTag(Long memSeq, Long festSeq) {
        Fest fest = festRepository.findByFestSeq(festSeq)
                .orElseThrow(() -> new BusinessException(ErrorCode.FEST_NOT_FOUND));

        int total = reviewRepository.countAllByFestAndDeletedIsFalse(fest);

        List<Review> reviewList = reviewRepository.findByFestAndDeletedIsFalse(fest);

        Map<Integer, Integer> tagMap = reviewList.stream()
                .flatMap(review -> tagRepository.findTagsByReview(review).stream())
                .collect(Collectors.toMap(
                        Function.identity(),
                        tag -> 1,
                        Integer::sum
                ));

        List<TagResDto> tagResList = tagMap.entrySet().stream()
                .map(integerIntegerEntry -> TagResDto.builder()
                        .tag(integerIntegerEntry.getKey())
                        .cnt(integerIntegerEntry.getValue().intValue())
                        .build())
                .sorted(Comparator.comparingInt(TagResDto::getTag))
                .collect(Collectors.toList());

        return GetTagRes.builder()
                .total(total)
                .tag(tagResList)
                .build();
    }

}
