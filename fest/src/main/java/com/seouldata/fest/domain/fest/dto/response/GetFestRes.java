package com.seouldata.fest.domain.fest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetFestRes {

    private Long festSeq;

    private String title;

    private String codeName;

    private String guname;

    private String place;

    private String useTrgt;

    private String isFree;

    private String useFee;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private double lot;

    private double lat;

    private String orgLink;

    private String mainImg;

    private double avgPoint;

    private int cntReview;

    private boolean isContinue;

    private boolean isHeart;

    private List<TagRes> tag;

}
