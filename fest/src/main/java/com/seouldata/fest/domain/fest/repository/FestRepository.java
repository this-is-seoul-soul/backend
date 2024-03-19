package com.seouldata.fest.domain.fest.repository;

import com.seouldata.fest.domain.fest.entity.Fest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FestRepository extends JpaRepository<Fest, Long> {

    int countAllByIsPublic(boolean isPublic);

    Optional<Fest> findByFestSeq(Long festSeq);

}
