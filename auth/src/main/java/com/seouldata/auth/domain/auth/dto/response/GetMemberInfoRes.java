package com.seouldata.auth.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMemberInfoRes {

    private String email;

    private String nickname;

    private String profile;

    private String mbti;

    private Boolean notification;

}
