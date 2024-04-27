package com.seouldata.fest.domain.fest.dto.response;

import lombok.*;

@ToString
@Getter
public class GetFestByCriteriaResDto {

    private Long festSeq;

    private String title;

    private Double lot;

    private Double lat;

    @Setter
    private boolean heart;

    public GetFestByCriteriaResDto(Long festSeq, String title, Double lot, Double lat) {
        this.festSeq = festSeq;
        this.title = title;
        this.lot = lot;
        this.lat = lat;
    }

}
