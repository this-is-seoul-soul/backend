package com.seouldata.fest.domain.fest.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetFestResDto {

    private CulturalEventInfo culturalEventInfo;

    @Data
    @RequiredArgsConstructor
    public static class CulturalEventInfo {

        @JsonProperty("list_total_count")
        private int listTotalCount;

        private List<Row> row;

    }

    @Data
    public static class Row {

        @JsonProperty("CODENAME")
        private String codename;

        @JsonProperty("GUNAME")
        private String guname;

        @JsonProperty("TITLE")
        private String title;

        @JsonProperty("ORG_NAME")
        private String orgName;

        @JsonProperty("USE_TRGT")
        private String useTrgt;

        @JsonProperty("USE_FEE")
        private String useFee;

        @JsonProperty("ORG_LINK")
        private String orgLink;

        @JsonProperty("MAIN_IMG")
        private String mainImg;

        @JsonProperty("STRTDATE")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
        private LocalDateTime startDate;

        @JsonProperty("END_DATE")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
        private LocalDateTime endDate;

        @JsonProperty("LOT")
        private String lot;

        @JsonProperty("LAT")
        private String lat;

        @JsonProperty("IS_FREE")
        private String IS_FREE;

    }

}