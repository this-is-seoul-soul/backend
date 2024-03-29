package com.seouldata.fest.domain.fest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AddFestReq {

    @NotBlank
    @Length(max = 100)
    private String title;

    @NotBlank
    private String codeName;

    @NotBlank
    @Length(max = 4)
    private String guName;

    @NotBlank
    @Length(max = 160)
    private String place;

    @NotBlank
    @Length(max = 90)
    private String useTrgt;

    @Length(min = 2, max = 2)
    private String isFree;

    @NotBlank
    @Length(max = 400)
    private String useFee;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private double lot;
    private double lat;

    @Length(max = 550)
    private String orgLink;

    @Length(max = 120)
    private String mainImg;

}
