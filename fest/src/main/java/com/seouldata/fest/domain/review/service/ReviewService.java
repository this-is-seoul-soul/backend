package com.seouldata.fest.domain.review.service;

import com.seouldata.fest.domain.review.dto.request.AddReviewReq;
import com.seouldata.fest.domain.review.dto.request.ModifyReviewReq;

public interface ReviewService {

    Long addReview(Long memSeq, AddReviewReq addReviewReq);

    void modifyReview(Long memSeq, ModifyReviewReq modifyReviewReq);

}
