package com.seouldata.fest.domain.fest.repository;

import com.seouldata.fest.domain.fest.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
