package com.seouldata.fest.domain.review.repository;

import com.seouldata.fest.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByReviewSeq(Long reviewSeq);

}
