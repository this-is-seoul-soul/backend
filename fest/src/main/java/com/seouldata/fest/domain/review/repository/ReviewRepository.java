package com.seouldata.fest.domain.review.repository;

import com.seouldata.fest.domain.fest.entity.Fest;
import com.seouldata.fest.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByReviewSeq(Long reviewSeq);

    @Query(value = "select nullif(avg(r.point), 0) from Review r where r.memSeq = :memSeq and r.fest = :fest and r.isDeleted = false")
    Double findPointByMemSeqAndFest(@Param("memSeq") Long memSeq, @Param("fest") Fest fest);

    @Query(value = "select nullif(avg(r.point), 0) from Review r where r.fest = :fest and r.isDeleted = false")
    Double findPointByFest(@Param("fest") Fest fest);

    @Query(value = "select count(*) from Review r where r.memSeq = :memSeq and r.fest = :fest and r.isDeleted = false")
    int countAllByMemSeqAndFestAndDeletedIsFalse(@Param("memSeq") Long memSeq, @Param("fest") Fest fest);

    @Query(value = "select count(*) from Review r where r.fest = :fest and r.isDeleted = false")
    int countAllByFestAndDeletedIsFalse(@Param("fest") Fest fest);

    @Query(value = "select r from Review r where r.fest = :fest and r.isDeleted = false")
    List<Review> findByFestAndDeletedIsFalse(@Param("fest") Fest fest);

    @Query(value = "select r from Review r where r.fest.festSeq = :festSeq and r.isDeleted = false")
    List<Review> findByFestSeq(@Param("festSeq") Long festSeq);

}
