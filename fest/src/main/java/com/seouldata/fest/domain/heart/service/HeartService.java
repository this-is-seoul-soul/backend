package com.seouldata.fest.domain.heart.service;

import com.seouldata.fest.domain.heart.dto.response.GetHeartRes;

import java.util.List;

public interface HeartService {

    void addHeart(Long memSeq, Long festSeq);

    void removeHeart(Long heartSeq);

    List<GetHeartRes> getHeart(Long memSeq);

}
