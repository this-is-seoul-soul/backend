package com.seouldata.auth.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleLoginRes {

    private String googleId;

    private String accessToken;

    private String refreshToken;

}
