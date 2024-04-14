package com.seouldata.auth.domain.auth.controller;

import com.seouldata.auth.domain.auth.dto.request.JoinMemberReq;
import com.seouldata.auth.domain.auth.service.AuthService;
import com.seouldata.common.response.EnvelopResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<EnvelopResponse> join(
            @RequestPart(value = "profile", required = false) MultipartFile profile,
            @RequestPart(value = "memberInfo") JoinMemberReq memberInfo
    ) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EnvelopResponse.builder()
                        .data(authService.join(memberInfo, profile))
                        .build()
                );
    }

    @GetMapping("/nickname/duplicate")
    public ResponseEntity<EnvelopResponse> checkNicknameDuplicate(
            @RequestParam(value = "nickname") String nickname
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder()
                        .data(authService.checkNicknameDuplicate(nickname))
                        .build()
        );

    @GetMapping("/nickname")
    public ResponseEntity<EnvelopResponse> createRandomNickname() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder()
                        .data(authService.createRandomNickname())
                        .build()
                );

    @GetMapping("/login/google")
    ResponseEntity<EnvelopResponse> googleLogin(
            @RequestParam(value = "googleId") String googleId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                        .body(EnvelopResponse.builder()
                                .data(authService.googleLogin(googleId))
                                .build()
                        );

    }

}
