package com.seouldata.auth.domain.auth.controller;

import com.seouldata.auth.domain.auth.dto.request.JoinMemberReq;
import com.seouldata.auth.domain.auth.dto.request.ModifyMbtiReq;
import com.seouldata.auth.domain.auth.dto.request.ModifyNicknameReq;
import com.seouldata.auth.domain.auth.dto.response.GetMemberSeqInfoRes;
import com.seouldata.auth.domain.auth.service.AuthService;
import com.seouldata.auth.global.annotation.Authorization;
import com.seouldata.common.response.EnvelopResponse;
import jakarta.annotation.Nullable;
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

    @PatchMapping("/nickname")
    public ResponseEntity<EnvelopResponse> updateNickname(
            @Authorization long memberSeq,
            @RequestBody ModifyNicknameReq modifyNicknameReq
    ) {
        authService.modifyNickname(memberSeq, modifyNicknameReq);

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder()
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
    }

    @GetMapping("/nickname")
    public ResponseEntity<EnvelopResponse> createRandomNickname() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder()
                        .data(authService.createRandomNickname())
                        .build()
                );
    }

    @GetMapping("/login/google")
    public ResponseEntity<EnvelopResponse> googleLogin(
            @RequestParam(value = "googleId") String googleId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder()
                        .data(authService.googleLogin(googleId))
                        .build()
                );

    }

    @GetMapping("/info")
    public ResponseEntity<EnvelopResponse> getMemberInfo(
            @Authorization long memberSeq
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder()
                        .data(authService.getMemberInfo(memberSeq))
                        .build()
                );
    }

    @GetMapping("/createInfo")
    public ResponseEntity<EnvelopResponse> getReviewWriterInfo(
            @RequestParam(value = "memSeq") long memSeq
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder()
                        .data(authService.getReviewWriterInfo(memSeq))
                        .build()
                );
    }

    @PatchMapping("/mbti")
    public ResponseEntity<EnvelopResponse> updateMbti(
            @Authorization long memberSeq,
            @RequestBody ModifyMbtiReq mbti
    ) {
        authService.modifyMbti(memberSeq, mbti);

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder()
                        .build()
                );
    }

    @GetMapping("/logout")
    public ResponseEntity<EnvelopResponse> logout(
            @RequestHeader(value = "Authorization") String token
    ) {
        authService.logout(token);

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder()
                        .build()
                );
    }

    @GetMapping("token/new")
    public ResponseEntity<EnvelopResponse> generateNewToken(
            @RequestHeader(value = "Authorization") String token
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder()
                        .data(authService.generateNewToken(token))
                        .build()
                );
    }

    @DeleteMapping("/quit")
    public ResponseEntity<EnvelopResponse> quit(
            @RequestHeader("Authorization") String token
    ) {
        authService.quit(token);

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder()
                        .build()
                );
    }

    @GetMapping("/status")
    public ResponseEntity<EnvelopResponse> checkStatus(
            @RequestHeader("Authorization") @Nullable String token,
            @RequestParam(value = "googleId") @Nullable String googleId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder()
                        .data(authService.checkStatus(token, googleId))
                        .build()
                );
    }

    @GetMapping("/memInfo")
    public ResponseEntity<EnvelopResponse> getMemInfo(
            @Authorization Long memSeq
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder()
                        .data(GetMemberSeqInfoRes.builder()
                                .memberSeq(memSeq)
                                .build())
                        .build()
                );
    }

}
