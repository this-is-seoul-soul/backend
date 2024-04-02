package com.seouldata.fest.domain.review.repository;

import com.seouldata.fest.domain.review.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(value = "select i from Image i where i.review.reviewSeq = :reviewSeq")
    List<Image> findImagesByReviewSeq(@Param("reviewSeq") Long reviewSeq);

    @Modifying
    @Query(value = "delete from Image where review.reviewSeq = :reviewSeq")
    void deleteAllByReviewSeq(@Param("reviewSeq") Long reviewSeq);

}
