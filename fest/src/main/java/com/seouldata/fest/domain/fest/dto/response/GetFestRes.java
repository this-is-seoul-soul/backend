package com.seouldata.fest.domain.fest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class GetFestRes {

    private Long festSeq;

    private String title;

    private String codename;

    private String mainImg;

    private LocalDate startDate;

    private LocalDate endDate;

    private String useFee;

    private double avgPoint;

    private int cntReview;

    private boolean isContinue;

    private boolean isHeart;

}
