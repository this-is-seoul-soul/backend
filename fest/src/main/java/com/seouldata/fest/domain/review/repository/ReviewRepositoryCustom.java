package com.seouldata.fest.domain.review.repository;

import com.seouldata.fest.domain.fest.entity.Fest;
import com.seouldata.fest.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepositoryCustom {

    List<Review> findAllWithCursor(Fest fest, int sort, Pageable pageable);

}
