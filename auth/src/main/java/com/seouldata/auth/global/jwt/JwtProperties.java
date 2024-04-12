package com.seouldata.auth.global.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    // TODO: final로 선언해서 생성자로 주입받는 방식으로 변경
    // 현재는 setter로 binding하기 때문에 final 사용 못함
    private String secret;
    private long accessTokenValidity;
    private long refreshTokenValidity;

}
