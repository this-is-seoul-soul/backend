package com.seouldata.fest.domain.fest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindByCodeReq {

    @NotBlank
    private String codename;

    @NotNull
    private boolean isFree;

    @NotNull
    private boolean isContinue;

    @Length(max = 4)
    @NotBlank
    private String region;

    private int sort;

    private int page;

    private int limit;

}
