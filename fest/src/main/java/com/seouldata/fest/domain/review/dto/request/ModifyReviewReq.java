package com.seouldata.fest.domain.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ModifyReviewReq {

    @NotNull
    private Long reviewSeq;

    @Length(max = 150)
    private String content;

    @Min(0)
    @Max(5)
    private int point;

    private List<String> imgUrl;

    private List<Integer> tag;

}
