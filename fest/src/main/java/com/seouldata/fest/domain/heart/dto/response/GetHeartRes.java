package com.seouldata.fest.domain.heart.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class GetHeartRes {

    private Long festSeq;

    private String title;

    private String codeName;

    private String mainImg;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String useFee;

    private double avgPoint;

    private int cntReview;

    private boolean isContinue;

    private boolean isHeart;

}
