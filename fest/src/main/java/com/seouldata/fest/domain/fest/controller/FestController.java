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
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/test")
    public ResponseEntity<EnvelopResponse> test() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder()
                        .code(HttpStatus.OK.value())
                        .data("test")
                        .build());
    }

    @PostMapping
    public ResponseEntity<EnvelopResponse> addFest(HttpServletRequest request, @RequestBody @Valid AddFestReq addFestReq) {

        festService.addFest((Long) request.getAttribute("memSeq"), addFestReq);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EnvelopResponse.builder().code(HttpStatus.CREATED.value()).build());
    }

    @PutMapping
    public ResponseEntity<EnvelopResponse> modifyFest(HttpServletRequest request, @RequestBody @Valid ModifyFestReq modifyFestReq) {

        festService.updateFest((Long) request.getAttribute("memSeq"), modifyFestReq);

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().code(HttpStatus.OK.value()).build());
    }

    @DeleteMapping
    public ResponseEntity<EnvelopResponse> removeFest(HttpServletRequest request, @RequestParam("festSeq") Long festSeq) {

        festService.removeFest((Long) request.getAttribute("memSeq"), festSeq);

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().code(HttpStatus.OK.value()).build());
    }

    @GetMapping("/search/detail")
    public ResponseEntity<EnvelopResponse> getFestDetail(HttpServletRequest request, @RequestParam("festSeq") Long festSeq) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().data(festService.getFestDetail((Long) request.getAttribute("memSeq"), festSeq)).code(HttpStatus.OK.value()).build());
    }

    @GetMapping("/search")
    public ResponseEntity<EnvelopResponse> getFestByCode(HttpServletRequest request,
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
                .body(EnvelopResponse.builder().data(festService.getFestByCode((Long) request.getAttribute("memSeq"), findByCodeReq)).code(HttpStatus.OK.value()).build());
    }

    @GetMapping("/search/map")
    public ResponseEntity<EnvelopResponse> getFestByCriteria(HttpServletRequest request,
                                                             @RequestParam("lot") @NotNull double lot,
                                                             @RequestParam("lat") @NotNull double lat,
                                                             @RequestParam("distance") @NotNull int distance,
                                                             @ValidFilterValues @RequestParam(value = "filter", required = false) List<String> filter,
                                                             @ValidYear @RequestParam(value = "year", required = false) List<Integer> year,
                                                             @ValidCodeName @RequestParam(value = "codeName", required = false) List<String> codeName) {

        FindFestByCriteriaReq findFestByCriteriaReq = FindFestByCriteriaReq.builder()
                .lot(lot)
                .lat(lat)
                .distance(distance)
                .filter(filter)
                .year(year)
                .codename(codeName)
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().data(festService.getFestByCriteria((Long) request.getAttribute("memSeq"), findFestByCriteriaReq)).code(HttpStatus.OK.value()).build());
    }

    @GetMapping
    public ResponseEntity<EnvelopResponse> getFestList(HttpServletRequest request, @RequestParam("keyword") String keyword, @RequestParam("lot") double lot, @RequestParam("lat") double lat) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().data(festService.getFestList((Long) request.getAttribute("memSeq"), keyword, lot, lat)).code(HttpStatus.OK.value()).build());
    }

    @GetMapping("/recommend")
    public ResponseEntity<EnvelopResponse> getRecommendFest(HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().data(festService.getRecommendFest((Long) request.getAttribute("memSeq"))).code(HttpStatus.OK.value()).build());
    }

    @GetMapping("/mine")
    public ResponseEntity<EnvelopResponse> getMyFest(HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopResponse.builder().data(festService.getMyFest((Long) request.getAttribute("memSeq"))).build());
    }

}
