package com.seouldata.fest.domain.review.repository;

import com.seouldata.fest.domain.review.entity.Review;
import com.seouldata.fest.domain.review.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query(value = "select t from Tag t where t.memSeq = :memSeq and t.review.reviewSeq = :reviewSeq")
    List<Tag> findTagsByMemSeqAndReviewSeq(@Param("memSeq") Long memSeq, @Param("reviewSeq") Long reviewSeq);

    @Modifying
    @Query(value = "delete from Tag where review.reviewSeq = :reviewSeq")
    void deleteAllByReviewSeq(@Param("reviewSeq") Long reviewSeq);

    @Query(value = "select t.tagNo from Tag t where t.review = :review and t.isDeleted = false")
    List<Integer> findTagsByReview(@Param("review") Review review);

}
