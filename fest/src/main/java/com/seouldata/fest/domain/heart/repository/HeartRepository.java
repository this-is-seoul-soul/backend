package com.seouldata.fest.domain.heart.repository;

import com.seouldata.fest.domain.fest.entity.Fest;
import com.seouldata.fest.domain.heart.entity.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findByMemSeqAndFest(Long memSeq, Fest fest);

    @Query(value = "select f from Heart h left join Fest f on h.fest.festSeq = f.festSeq where h.memSeq = :memSeq")
    List<Fest> findFestByMemSeq(@Param("memSeq") Long memSeq);

    boolean existsByFestAndAndMemSeq(Fest fest, long memSeq);

}
