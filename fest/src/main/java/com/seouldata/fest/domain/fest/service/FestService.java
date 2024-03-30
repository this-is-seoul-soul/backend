package com.seouldata.fest.domain.fest.service;

import com.seouldata.fest.domain.fest.dto.request.AddFestReq;

public interface FestService {

    void getFestData();

    void addFest(Long memSeq, AddFestReq addFestReq);

}
