package com.seouldata.fest.global.interceptor;

import com.seouldata.common.exception.BusinessException;
import com.seouldata.common.exception.ErrorCode;
import com.seouldata.common.response.EnvelopResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class AccessTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("Authorization");

        if (accessToken == null) {
            throw new BusinessException(ErrorCode.TOKEN_NOT_EXIST);
        }

        if (accessToken.contains("Bearer")) {
            accessToken = accessToken.split(" ")[1];
        }

        Long memSeq = convertAccessToken(accessToken);
        request.setAttribute("memSeq", memSeq);
        return true;
    }

    private Long convertAccessToken(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<EnvelopResponse> responseEntity = restTemplate.exchange(
                "https://seoulsoul.site/member/memInfo",
                HttpMethod.GET,
                entity,
                EnvelopResponse.class
        );

        System.out.println(responseEntity.getBody().getData());

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String res = responseEntity.getBody().getData().toString();
            res = res.replace("}", "");
            res = res.substring(11);
            return Long.parseLong(res);
        } else {
            throw new BusinessException(ErrorCode.FAIL_MEMBER_INFO);
        }
    }

}