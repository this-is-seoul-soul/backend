package com.seouldata.fest.domain.fest.repository;

import com.seouldata.fest.domain.fest.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
