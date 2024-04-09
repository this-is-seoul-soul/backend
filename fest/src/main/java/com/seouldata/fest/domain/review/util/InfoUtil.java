package com.seouldata.fest.domain.review.util;

import com.seouldata.fest.domain.review.dto.response.GetMemberInfoRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class InfoUtil {

    @Value("${member.server.url}")
    private String memberServerUrl;

    public GetMemberInfoRes getMemberInfo(Long memSeq) {
        WebClient webClient = WebClient.create(memberServerUrl);

        GetMemberInfoRes info = webClient.get()
                .uri("/member/creatInfo")
                .header("memSeq", String.valueOf(memSeq))
                .retrieve()
                .bodyToMono(GetMemberInfoRes.class)
                .block();

        return info;
    }

}
