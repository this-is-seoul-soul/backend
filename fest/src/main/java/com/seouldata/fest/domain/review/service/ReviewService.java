package com.seouldata.fest.domain.review.service;

import com.seouldata.fest.domain.review.dto.request.AddReviewReq;
import com.seouldata.fest.domain.review.dto.request.ModifyReviewReq;
import com.seouldata.fest.domain.review.dto.response.GetReviewRes;
import com.seouldata.fest.domain.review.dto.response.GetReviewTotalRes;
import com.seouldata.fest.domain.review.dto.response.GetTagRes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {

    GetReviewTotalRes findReview(Long memSeq, Long festSeq, int sort, int page, int limit);

    void addReview(Long memSeq, AddReviewReq addReviewReq, List<MultipartFile> images);

    void modifyReview(Long memSeq, ModifyReviewReq modifyReviewReq);

    void removeReview(Long memSeq, Long reviewSeq);

    GetTagRes findTag(Long memSeq, Long festSeq);

    List<GetReviewRes> findMyReview(Long memSeq);

}
