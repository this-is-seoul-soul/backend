package com.seouldata.fest.domain.review.controller;

import com.seouldata.common.response.EnvelopResponse;
import com.seouldata.fest.domain.review.dto.request.AddReviewReq;
import com.seouldata.fest.domain.review.dto.request.ModifyReviewReq;
import com.seouldata.fest.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fest/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<EnvelopResponse> findReview(@RequestHeader("memSeq") Long memSeq, @RequestParam("festSeq") Long festSeq,
                                                      @RequestParam("sort") int sort, @RequestParam("page") int page, @RequestParam("limit") int limit) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().data(reviewService.findReview(memSeq, festSeq, sort, page, limit)).build());
    }

    @PostMapping
    public ResponseEntity<EnvelopResponse> addReview(@RequestHeader("memSeq") Long memSeq, @RequestBody @Valid AddReviewReq addReviewReq) {

        reviewService.addReview(memSeq, addReviewReq);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EnvelopResponse.builder().code(HttpStatus.CREATED.value()).build());
    }

    @PatchMapping
    public ResponseEntity<EnvelopResponse> modifyReview(@RequestHeader("memSeq") Long memSeq, @RequestBody @Valid ModifyReviewReq modifyReviewReq) {

        reviewService.modifyReview(memSeq, modifyReviewReq);

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().code(HttpStatus.OK.value()).build());
    }

    @DeleteMapping("/{reviewSeq}")
    public ResponseEntity<EnvelopResponse> removeReview(@RequestHeader("memSeq") Long memSeq, @PathVariable("reviewSeq") Long reviewSeq) {

        reviewService.removeReview(memSeq, reviewSeq);

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().code(HttpStatus.OK.value()).build());
    }

    @GetMapping("/tag/{festSeq}")
    public ResponseEntity<EnvelopResponse> findTag(@RequestHeader("memSeq") Long memSeq, @PathVariable("festSeq") Long festSeq) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().data(reviewService.findTag(memSeq, festSeq)).build());
    }

}
