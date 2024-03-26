package com.seouldata.fest.domain.review.repository;

import com.seouldata.fest.domain.review.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {


    @Modifying
    @Query(value = "delete from Image where review.reviewSeq = :reviewSeq")
    void deleteAllByReviewSeq(@Param("reviewSeq") Long reviewSeq);

}
