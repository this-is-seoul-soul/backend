package com.seouldata.auth.domain.badge.controller;

import com.seouldata.auth.domain.badge.dto.request.UpdateBadgeReq;
import com.seouldata.auth.domain.badge.service.BadgeService;
import com.seouldata.auth.global.annotation.Authorization;
import com.seouldata.common.response.EnvelopResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/badge")
public class BadgeController {

    private final BadgeService badgeService;

    @GetMapping
    public ResponseEntity<EnvelopResponse> getAllBadges(
            @Authorization long memberSeq
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder()
                        .data(badgeService.getAllBadges(memberSeq))
                        .build()
                );
    }

    @PatchMapping
    public ResponseEntity<EnvelopResponse> updateMyBadge(
            @Authorization long memberSeq,
            @RequestBody UpdateBadgeReq updateMyBadgeReq
    ) {
        badgeService.updateMyBadge(memberSeq, updateMyBadgeReq);

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder()
                        .build()
                );
    }

}
