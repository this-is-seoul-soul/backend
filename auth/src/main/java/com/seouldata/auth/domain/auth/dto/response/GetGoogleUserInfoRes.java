package com.seouldata.auth.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class GetGoogleUserInfoRes {
    private String id;
    private String email;
}
