package com.seouldata.fest.domain.fest.service;

import com.seouldata.fest.domain.fest.dto.request.AddFestReq;
import com.seouldata.fest.domain.fest.dto.request.ModifyFestReq;
import com.seouldata.fest.domain.fest.dto.response.GetFestRes;

public interface FestService {

    void getFestData();

    void addFest(Long memSeq, AddFestReq addFestReq);

    void updateFest(Long memSeq, ModifyFestReq modifyFestReq);

    void removeFest(Long memSeq, Long festSeq);

    GetFestRes getFestDetail(Long memSeq, Long festSeq);

}
