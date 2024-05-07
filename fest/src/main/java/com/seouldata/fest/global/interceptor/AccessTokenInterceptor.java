package com.seouldata.fest.global.interceptor;

import com.seouldata.fest.global.response.GetMemberSeqInfoRes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.HandlerInterceptor;

public class AccessTokenInterceptor implements HandlerInterceptor {

    @Value("${member.server.url}")
    private String memberServerUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("Authorization");

        Long memSeq = convertAccessToken(accessToken);
        request.removeAttribute("Authorization");
        request.setAttribute("memSeq", memSeq);

        return true;
    }

    private Long convertAccessToken(String accessToken) {
        WebClient webClient = WebClient.create(memberServerUrl);

        GetMemberSeqInfoRes responseEntity = webClient.get()
                .uri("/member/memInfo")
                .header("Authorization", accessToken)
                .retrieve()
                .bodyToMono(GetMemberSeqInfoRes.class)
                .block();

        return responseEntity.getMemberSeq();
    }

}
