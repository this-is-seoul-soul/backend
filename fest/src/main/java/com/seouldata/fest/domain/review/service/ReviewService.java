package com.seouldata.fest.domain.review.service;

import com.seouldata.fest.domain.review.dto.request.AddReviewReq;
import com.seouldata.fest.domain.review.dto.request.ModifyReviewReq;
import com.seouldata.fest.domain.review.dto.response.GetReviewTotalRes;
import com.seouldata.fest.domain.review.dto.response.GetTagRes;

public interface ReviewService {

    GetReviewTotalRes findReview(Long memSeq, Long festSeq, int sort, int page, int limit);

    Long addReview(Long memSeq, AddReviewReq addReviewReq);

    void modifyReview(Long memSeq, ModifyReviewReq modifyReviewReq);

    void removeReview(Long memSeq, Long reviewSeq);

    GetTagRes findTag(Long memSeq, Long festSeq);

}
