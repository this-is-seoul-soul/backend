package com.seouldata.fest.domain.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMemberInfoRes {

    private String nickname;

    private String mbti;

}
