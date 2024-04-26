package com.seouldata.fest.domain.fest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TagRes {

    private int tag;

    private int cnt;

}
