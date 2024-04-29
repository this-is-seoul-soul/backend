package com.seouldata.auth.domain.badge.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllBadgesRes {

    private int seq;

    private String title;

    private String image;

    private Boolean isOwn;

}
