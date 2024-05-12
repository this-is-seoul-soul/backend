package com.seouldata.fest.domain.heart.controller;

import com.seouldata.common.response.EnvelopResponse;
import com.seouldata.fest.domain.heart.service.HeartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fest/heart")
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/{festSeq}")
    public ResponseEntity<EnvelopResponse> addHeart(HttpServletRequest request, @PathVariable("festSeq") Long festSeq) {

        heartService.addHeart((Long) request.getAttribute("memSeq"), festSeq);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EnvelopResponse.builder().code(HttpStatus.CREATED.value()).build());
    }

    @DeleteMapping("/{festSeq}")
    public ResponseEntity<EnvelopResponse> removeHeart(HttpServletRequest request, @PathVariable("festSeq") Long festSeq) {

        heartService.removeHeart((Long) request.getAttribute("memSeq"), festSeq);

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().code(HttpStatus.OK.value()).build());
    }

    @GetMapping
    public ResponseEntity<EnvelopResponse> getHeart(HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().data(heartService.getHeart((Long) request.getAttribute("memSeq"))).code(HttpStatus.OK.value()).build());
    }

}
