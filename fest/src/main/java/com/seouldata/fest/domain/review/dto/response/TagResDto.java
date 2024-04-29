package com.seouldata.fest.domain.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TagResDto {

    private int tag;

    private int cnt;

}
