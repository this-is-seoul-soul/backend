package com.seouldata.fest.domain.fest.controller;

import com.seouldata.common.response.EnvelopResponse;
import com.seouldata.fest.domain.fest.annotation.validation.ValidCodeName;
import com.seouldata.fest.domain.fest.annotation.validation.ValidFilterValues;
import com.seouldata.fest.domain.fest.annotation.validation.ValidYear;
import com.seouldata.fest.domain.fest.dto.request.AddFestReq;
import com.seouldata.fest.domain.fest.dto.request.FindByCodeReq;
import com.seouldata.fest.domain.fest.dto.request.FindFestByCriteriaReq;
import com.seouldata.fest.domain.fest.dto.request.ModifyFestReq;
import com.seouldata.fest.domain.fest.service.FestService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fest")
public class FestController {

    private final FestService festService;

    @PostMapping
    public ResponseEntity<EnvelopResponse> addFest(@RequestHeader("memSeq") Long memSeq, @RequestBody @Valid AddFestReq addFestReq) {

        festService.addFest(memSeq, addFestReq);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EnvelopResponse.builder().code(HttpStatus.CREATED.value()).build());
    }

    @PutMapping
    public ResponseEntity<EnvelopResponse> modifyFest(@RequestHeader("memSeq") Long memSeq, @RequestBody @Valid ModifyFestReq modifyFestReq) {

        festService.updateFest(memSeq, modifyFestReq);

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().code(HttpStatus.OK.value()).build());
    }

    @DeleteMapping
    public ResponseEntity<EnvelopResponse> removeFest(@RequestHeader("memSeq") Long memSeq, @RequestParam("festSeq") Long festSeq) {

        festService.removeFest(memSeq, festSeq);

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().code(HttpStatus.OK.value()).build());
    }

    @GetMapping("/search/detail")
    public ResponseEntity<EnvelopResponse> getFestDetail(@RequestHeader("memSeq") Long memSeq, @RequestParam("festSeq") Long festSeq) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().data(festService.getFestDetail(memSeq, festSeq)).code(HttpStatus.OK.value()).build());
    }

    @GetMapping("/search")
    public ResponseEntity<EnvelopResponse> getFestByCode(@RequestHeader("memSeq") Long memSeq,
                                                         @RequestParam("codename") String codename,
                                                         @RequestParam("isFree") boolean isFree,
                                                         @RequestParam("isContinue") boolean isContinue,
                                                         @RequestParam(value = "region") String region,
                                                         @RequestParam("sort") int sort,
                                                         @RequestParam("page") int page,
                                                         @RequestParam("limit") int limit) {

        FindByCodeReq findByCodeReq = FindByCodeReq.builder()
                .codename(codename)
                .isFree(isFree)
                .isContinue(isContinue)
                .region(region)
                .sort(sort)
                .page(page)
                .limit(limit)
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().data(festService.getFestByCode(memSeq, findByCodeReq)).code(HttpStatus.OK.value()).build());
    }

    @GetMapping("/search/map")
    public ResponseEntity<EnvelopResponse> getFestByCriteria(@RequestHeader("memSeq") Long memSeq, @RequestBody FindFestByCriteriaReq findFestByCriteriaReq) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().data(festService.getFestByCriteria(memSeq, findFestByCriteriaReq)).code(HttpStatus.OK.value()).build());
    }

    @GetMapping
    public ResponseEntity<EnvelopResponse> getFestList(@RequestHeader("memSeq") Long memSeq, @RequestParam("keyword") String keyword, @RequestParam("lot") double lot, @RequestParam("lat") double lat) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().data(festService.getFestList(memSeq, keyword, lot, lat)).code(HttpStatus.OK.value()).build());
    }

    @GetMapping("/recommend")
    public ResponseEntity<EnvelopResponse> getRecommendFest(@RequestHeader("memSeq") Long memSeq) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().data(festService.getRecommendFest(memSeq)).code(HttpStatus.OK.value()).build());
    }

}
