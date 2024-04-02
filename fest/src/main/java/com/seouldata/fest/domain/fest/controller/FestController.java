package com.seouldata.fest.domain.fest.controller;

import com.seouldata.common.response.EnvelopResponse;
import com.seouldata.fest.domain.fest.dto.request.AddFestReq;
import com.seouldata.fest.domain.fest.dto.request.ModifyFestReq;
import com.seouldata.fest.domain.fest.service.FestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
