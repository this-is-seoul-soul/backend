package com.seouldata.fest.domain.fest.repository;

import com.seouldata.fest.domain.fest.dto.request.FindFestByCriteriaReq;
import com.seouldata.fest.domain.fest.dto.response.GetFestByCriteriaResDto;
import com.seouldata.fest.domain.fest.entity.Fest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FestRepository extends JpaRepository<Fest, Long>, FestRepositoryCustom {

    int countAllByIsPublic(boolean isPublic);

    Optional<Fest> findByFestSeq(Long festSeq);

    @Query(value = "select case when count(h) > 0 then true else false end from Fest f left join Heart h on f.festSeq = h.fest.festSeq where h.memSeq = :memSeq and h.fest.festSeq = :festSeq")
    boolean findHeartByMemSeqAndFestSeq(@Param("memSeq") Long memSeq, @Param("festSeq") Long festSeq);

    List<GetFestByCriteriaResDto> findAllByCriteria(Long memSeq, FindFestByCriteriaReq findFestByCriteriaReq);

    List<Fest> findByKeyword(String keyword);

    List<Fest> findByKeywordAndLocation(String keyword, double lot, double lat);

    List<Fest> findTop10ByCodenameInOrderByFestSeqDesc(List<Integer> festCoodenumList);

    @Query(value = "select f from Fest f where f.creator = :memSeq and f.isDeleted = false")
    List<Fest> findByCreator(@Param("memSeq") Long memSeq);

}
