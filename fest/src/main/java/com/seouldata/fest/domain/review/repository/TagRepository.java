package com.seouldata.fest.domain.review.repository;

import com.seouldata.fest.domain.review.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Modifying
    @Query(value = "delete from Tag where review.reviewSeq = :reviewSeq")
    void deleteAllByReviewSeq(@Param("reviewSeq") Long reviewSeq);

}
