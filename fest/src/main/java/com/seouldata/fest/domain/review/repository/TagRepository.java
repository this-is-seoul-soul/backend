package com.seouldata.fest.domain.review.repository;

import com.seouldata.fest.domain.review.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
