package com.seouldata.auth.domain.badge.service;

import com.seouldata.auth.domain.badge.dto.response.GetAllBadgesRes;

import java.util.List;

public interface BadgeService {

    List<GetAllBadgesRes> getAllBadges(long memberSeq);

}
