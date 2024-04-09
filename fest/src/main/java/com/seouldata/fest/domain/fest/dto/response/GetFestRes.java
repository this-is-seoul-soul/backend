package com.seouldata.fest.domain.fest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class GetFestRes {

    private Long festSeq;

    private String title;

    private String codename;

    private String mainImg;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String useFee;

    private double avgPoint;

    private int cntReview;

    private boolean isContinue;

    private boolean isHeart;

}
