package com.seouldata.fest.domain.fest.repository;

import com.seouldata.fest.domain.fest.dto.request.FindByCodeReq;
import com.seouldata.fest.domain.fest.dto.request.FindFestByCriteriaReq;
import com.seouldata.fest.domain.fest.dto.response.GetFestByCriteriaResDto;
import com.seouldata.fest.domain.fest.entity.Fest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FestRepositoryCustom {

    List<GetFestByCriteriaResDto> findAllByCriteria(Long memSeq, FindFestByCriteriaReq findFestByCriteriaReq);

    List<Fest> findByKeyword(String keyword);

    List<Fest> findByKeywordAndLocation(String keyword, double lot, double lat);

    List<Fest> findByCodeWithCursor(FindByCodeReq findByCodeReq, int sort, Pageable pageable);

}

