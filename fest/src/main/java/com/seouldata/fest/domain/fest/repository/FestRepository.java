package com.seouldata.fest.domain.fest.repository;

import com.seouldata.fest.domain.fest.entity.Fest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FestRepository extends JpaRepository<Fest, Long> {

    int countAllByIsPublic(boolean isPublic);

    Optional<Fest> findByFestSeq(Long festSeq);

    @Query(value = "select case when count(h) > 0 then true else false end from Fest f left join Heart h on f.festSeq = h.fest.festSeq where h.memSeq = :memSeq and h.fest.festSeq = :festSeq")
    boolean findHeartByMemSeqAndFestSeq(@Param("memSeq") Long memSeq, @Param("festSeq") Long festSeq);

    @Query(value = "select f from Fest f where f.codename = :codename and f.isDeleted = false")
    List<Fest> findFestByCodenameAndDeletedIsFalse(@Param("codename") int codename);

}
