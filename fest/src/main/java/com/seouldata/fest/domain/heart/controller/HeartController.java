package com.seouldata.fest.domain.heart.controller;

import com.seouldata.common.response.EnvelopResponse;
import com.seouldata.fest.domain.heart.service.HeartService;
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
    public ResponseEntity<EnvelopResponse> addHeart(@RequestHeader("memSeq") Long memSeq, @PathVariable("festSeq") Long festSeq) {

        heartService.addHeart(memSeq, festSeq);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EnvelopResponse.builder().code(HttpStatus.CREATED.value()).build());
    }

    @DeleteMapping("/{heartSeq}")
    public ResponseEntity<EnvelopResponse> removeHeart(@RequestHeader("memSeq") Long memSeq, @PathVariable("heartSeq") Long heartSeq) {

        heartService.removeHeart(heartSeq);

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().code(HttpStatus.OK.value()).build());
    }

    @GetMapping
    public ResponseEntity<EnvelopResponse> getHeart(@RequestHeader("memSeq") Long memSeq) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().data(heartService.getHeart(memSeq)).code(HttpStatus.OK.value()).build());
    }

}
