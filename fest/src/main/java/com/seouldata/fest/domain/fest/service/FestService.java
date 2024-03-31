package com.seouldata.fest.domain.fest.service;

import com.seouldata.fest.domain.fest.dto.request.AddFestReq;
import com.seouldata.fest.domain.fest.dto.request.ModifyFestReq;

public interface FestService {

    void getFestData();

    void addFest(Long memSeq, AddFestReq addFestReq);

    void updateFest(Long memSeq, ModifyFestReq modifyFestReq);

}
