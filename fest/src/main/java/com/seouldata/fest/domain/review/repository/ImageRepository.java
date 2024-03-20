package com.seouldata.fest.domain.review.repository;

import com.seouldata.fest.domain.review.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
