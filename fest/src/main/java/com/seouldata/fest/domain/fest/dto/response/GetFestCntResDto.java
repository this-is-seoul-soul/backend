package com.seouldata.fest.domain.fest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class GetFestCntResDto {

    private GetFestResDto.CulturalEventInfo culturalEventInfo;

    @Data
    @RequiredArgsConstructor
    public static class CulturalEventInfo {

        @JsonProperty("list_total_count")
        private int listTotalCount;

    }

}
