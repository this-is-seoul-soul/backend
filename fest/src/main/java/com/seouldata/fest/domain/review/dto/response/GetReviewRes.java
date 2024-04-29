package com.seouldata.fest.domain.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetReviewRes {

    private Long reviewSeq;

    private String nickName;

    private String mbti;

    private String content;

    private int point;

    private List<String> imgUrl;

    private boolean isMine;

}
