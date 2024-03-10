package com.seouldata.fest.domain.fest.repository;

import com.seouldata.fest.domain.fest.entity.Fest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FestRepository extends JpaRepository<Fest, Long> {

}
