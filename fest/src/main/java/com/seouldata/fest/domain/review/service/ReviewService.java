package com.seouldata.fest.domain.review.service;

import com.seouldata.fest.domain.review.dto.request.AddReviewReq;
import com.seouldata.fest.domain.review.dto.request.ModifyReviewReq;
import com.seouldata.fest.domain.review.dto.response.GetReviewRes;

import java.util.List;

public interface ReviewService {

    List<GetReviewRes> findReview(Long memSeq, Long festSeq);

    Long addReview(Long memSeq, AddReviewReq addReviewReq);

    void modifyReview(Long memSeq, ModifyReviewReq modifyReviewReq);

    void removeReview(Long memSeq, Long reviewSeq);

}
