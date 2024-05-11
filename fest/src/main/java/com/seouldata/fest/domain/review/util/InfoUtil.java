package com.seouldata.fest.domain.review.util;

import com.seouldata.common.exception.BusinessException;
import com.seouldata.common.exception.ErrorCode;
import com.seouldata.common.response.EnvelopResponse;
import com.seouldata.fest.domain.review.dto.response.GetMemberInfoRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@Component
@RequiredArgsConstructor
public class InfoUtil {

    public GetMemberInfoRes getMemberInfo(Long memSeq) {
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("https://seoulsoul.site/member/createInfo")
                .queryParam("memSeq", memSeq);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        URI uri = builder.build().toUri();
        ResponseEntity<EnvelopResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                EnvelopResponse.class
        );

        System.out.println(responseEntity.getBody().getData());

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String res = responseEntity.getBody().getData().toString();
            res = res.replaceAll("[{}]", "");
            String nickname = res.split(",")[0].split("=")[1];
            String mbti = res.split(",")[1].split("=")[1];
            return GetMemberInfoRes.builder().mbti(mbti).nickname(nickname).build();
        }

        throw new BusinessException(ErrorCode.FAIL_MEMBER_INFO);
    }

}
