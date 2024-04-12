package com.seouldata.fest.domain.fest.dto.request;

import com.seouldata.fest.domain.fest.annotation.validation.ValidCodeName;
import com.seouldata.fest.domain.fest.annotation.validation.ValidFilterValues;
import com.seouldata.fest.domain.fest.annotation.validation.ValidYear;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FindFestByCriteriaReq {

    @NotNull
    private double lot;

    @NotNull
    private double lat;

    @NotNull
    private int distance;

    @ValidFilterValues
    private List<String> filter;

    @ValidYear
    private List<Integer> year;

    @ValidCodeName
    private List<String> codename;

}
