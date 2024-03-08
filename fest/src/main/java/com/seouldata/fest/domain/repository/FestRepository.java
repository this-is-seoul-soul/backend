package com.seouldata.fest.domain.repository;

import com.seouldata.fest.domain.entity.Fest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FestRepository extends JpaRepository<Fest, Long> {

}
