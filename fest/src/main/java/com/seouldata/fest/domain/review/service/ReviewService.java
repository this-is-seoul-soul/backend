package com.seouldata.fest.domain.review.service;

import com.seouldata.fest.domain.review.dto.request.AddReviewReq;

public interface ReviewService {

    Long addReview(Long memSeq, AddReviewReq addReviewReq);

}
