package com.seouldata.auth.domain.auth.dto.request;

import lombok.Getter;

@Getter
public class JoinMemberReq {

    private String email;

    private String nickname;

    private String googleId;

}
