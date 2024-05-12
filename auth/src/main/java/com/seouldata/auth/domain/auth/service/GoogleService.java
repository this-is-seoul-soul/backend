package com.seouldata.auth.domain.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.seouldata.auth.domain.auth.dto.response.GetGoogleUserInfoRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
public class GoogleService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String LOGIN_REDIRECT_URL;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getGoogleAccessToken(String accessCode) {
        String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
        String decode = URLDecoder.decode(accessCode, StandardCharsets.UTF_8);

        System.out.println("================== Google Service: getGoogleAccessToken ==================");
        System.out.println("decode: " + decode);
        System.out.println();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", decode);
        params.add("client_id", GOOGLE_CLIENT_ID);
        params.add("client_secret", GOOGLE_CLIENT_SECRET);
        params.add("redirect_uri", LOGIN_REDIRECT_URL);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(GOOGLE_TOKEN_URL, HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();
        return accessTokenNode.get("access_token").asText();
    }

    public GetGoogleUserInfoRes getUserInfo(String accessToken) {
        String GOOGLE_USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(GOOGLE_USER_INFO_URL, HttpMethod.GET, entity, JsonNode.class);
        JsonNode userInfoNode = responseNode.getBody();

        // userInfoNode 제공데이터
        // id, email, verified_email, name, given_name,family_name, picture, locale

        return GetGoogleUserInfoRes.builder()
                .id(userInfoNode.get("id").asText())
                .email(userInfoNode.get("email").asText())
                .build();
    }

}
