package com.seouldata.fest.domain.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AddReviewReq {

    @NotBlank
    @Length(max = 150)
    private String content;

    @NotNull
    @Min(0)
    @Max(5)
    private int point;

//    private List<String> imgUrl;

    private List<Integer> tag;

    @NotNull
    private Long festSeq;

}
