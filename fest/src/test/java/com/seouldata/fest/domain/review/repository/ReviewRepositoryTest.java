package com.seouldata.fest.domain.review.repository;

import com.seouldata.fest.domain.fest.repository.FestRepository;
import com.seouldata.fest.domain.review.entity.Image;
import com.seouldata.fest.domain.review.entity.Review;
import com.seouldata.fest.domain.review.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ReviewRepositoryTest {

    @Autowired
    private FestRepository festRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Test
    public void InsertReviewDummy() {

        Review review = Review.builder()
                .memSeq(1L)
                .fest(festRepository.findById(1L).get())
                .point(5)
                .content("정말 최고입니다")
                .isDeleted(false)
                .build();

        reviewRepository.save(review);
    }

    @Test
    public void InsertTagDummy() {

        Tag tag = Tag.builder()
                .memSeq(1L)
                .review(reviewRepository.findById(1L).get())
                .tagNo(1)
                .isDeleted(false)
                .build();

        tagRepository.save(tag);
    }

    @Test
    public void InsertImageDummy() {

        Image image = Image.builder()
                .review(reviewRepository.findById(1L).get())
                .imgUrl("test_img")
                .isDeleted(false)
                .build();

        imageRepository.save(image);
    }

}
