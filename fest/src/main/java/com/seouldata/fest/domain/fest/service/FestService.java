package com.seouldata.fest.domain.fest.service;

import com.seouldata.fest.domain.fest.dto.request.AddFestReq;
import com.seouldata.fest.domain.fest.dto.request.FindFestByCriteriaReq;
import com.seouldata.fest.domain.fest.dto.request.ModifyFestReq;
import com.seouldata.fest.domain.fest.dto.response.GetFestDetailRes;
import com.seouldata.fest.domain.fest.dto.response.GetFestByCriteriaResDto;
import com.seouldata.fest.domain.fest.dto.response.GetFestRes;

import java.util.List;

public interface FestService {

    void getFestData();

    void addFest(Long memSeq, AddFestReq addFestReq);

    void updateFest(Long memSeq, ModifyFestReq modifyFestReq);

    void removeFest(Long memSeq, Long festSeq);

    GetFestDetailRes getFestDetail(Long memSeq, Long festSeq);

    List<GetFestRes> getFestByCode(Long memSeq, String codename);

    List<GetFestByCriteriaResDto> getFestByCriteria(Long memSeq, FindFestByCriteriaReq findFestByCriteriaReq);

}