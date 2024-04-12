package com.seouldata.fest.domain.fest.repository;

import com.seouldata.fest.domain.fest.dto.request.FindFestByCriteriaReq;
import com.seouldata.fest.domain.fest.dto.response.GetFestByCriteriaResDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FestRepositoryCustom {

    List<GetFestByCriteriaResDto> findAllByCriteria(Long memSeq, FindFestByCriteriaReq findFestByCriteriaReq);

}

