package com.seouldata.fest.domain.fest.service;

import com.seouldata.fest.domain.fest.dto.resquest.AddReviewReq;

public interface ReviewService {

    Long addReview(Long memSeq, AddReviewReq addReviewReq);

}
