package com.seouldata.fest.domain.heart.repository;

import com.seouldata.fest.domain.heart.entity.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findByHeartSeq(Long heartSeq);

}
