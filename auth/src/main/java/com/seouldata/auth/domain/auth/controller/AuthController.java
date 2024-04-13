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
    public ResponseEntity<EnvelopResponse> join (
            @RequestPart(value = "profile", required = false) MultipartFile profile,
            @RequestPart(value = "memberInfo") JoinMemberReq memberInfo
    ) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EnvelopResponse.builder()
                        .data(authService.join(memberInfo, profile))
                        .build()
                );
    }

}
