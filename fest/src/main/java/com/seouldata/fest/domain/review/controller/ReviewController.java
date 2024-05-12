package com.seouldata.fest.domain.review.controller;

import com.seouldata.common.response.EnvelopResponse;
import com.seouldata.fest.domain.review.dto.request.AddReviewReq;
import com.seouldata.fest.domain.review.dto.request.ModifyReviewReq;
import com.seouldata.fest.domain.review.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fest/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<EnvelopResponse> findReview(HttpServletRequest request, @RequestParam("festSeq") Long festSeq,
                                                      @RequestParam("sort") int sort, @RequestParam("page") int page, @RequestParam("limit") int limit) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().data(reviewService.findReview((Long) request.getAttribute("memSeq"), festSeq, sort, page, limit)).build());
    }

    @PostMapping
    public ResponseEntity<EnvelopResponse> addReview(
            HttpServletRequest request,
            @RequestPart @Valid AddReviewReq addReviewReq,
            @RequestPart(value = "imgUrl", required = false) List<MultipartFile> imgUrl
    ) {
        reviewService.addReview((Long) request.getAttribute("memSeq"), addReviewReq, imgUrl);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EnvelopResponse.builder().code(HttpStatus.CREATED.value()).build());
    }

    @PatchMapping
    public ResponseEntity<EnvelopResponse> modifyReview(HttpServletRequest request, @RequestBody @Valid ModifyReviewReq modifyReviewReq) {

        reviewService.modifyReview((Long) request.getAttribute("memSeq"), modifyReviewReq);

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().code(HttpStatus.OK.value()).build());
    }

    @DeleteMapping("/{reviewSeq}")
    public ResponseEntity<EnvelopResponse> removeReview(HttpServletRequest request, @PathVariable("reviewSeq") Long reviewSeq) {

        reviewService.removeReview((Long) request.getAttribute("memSeq"), reviewSeq);

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().code(HttpStatus.OK.value()).build());
    }

    @GetMapping("/tag/{festSeq}")
    public ResponseEntity<EnvelopResponse> findTag(HttpServletRequest request, @PathVariable("festSeq") Long festSeq) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().data(reviewService.findTag((Long) request.getAttribute("memSeq"), festSeq)).build());
    }

    @GetMapping("/mine")
    public ResponseEntity<EnvelopResponse> findMyReview(HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().data(reviewService.findMyReview((Long) request.getAttribute("memSeq"))).build());
    }

}
