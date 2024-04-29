package com.seouldata.fest.domain.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetTagRes {

    private int total;

    private List<TagResDto> tag;

}
