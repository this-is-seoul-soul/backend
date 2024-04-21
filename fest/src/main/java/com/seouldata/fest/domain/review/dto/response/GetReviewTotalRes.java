package com.seouldata.fest.domain.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetReviewTotalRes {

    private List<GetReviewRes> reviewResList;

    private long nextCursor;

    private int nextPage;

    private boolean hasNext;

}
