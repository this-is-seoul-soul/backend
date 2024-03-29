package com.seouldata.fest.domain.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seouldata.fest.domain.review.dto.request.AddReviewReq;
import com.seouldata.fest.domain.review.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @Test
    @DisplayName("리뷰 등록 성공")
    void addReview_success() throws Exception {
        AddReviewReq addReviewReq = new AddReviewReq("정말 좋아요", 4, List.of(""), List.of(3), 6L);
        Long memSeq = 1L;

        // stub
//        BDDMockito.given(reviewService.addReview(eq(1L), any(AddReviewReq.class))).willReturn(eq(1L));

        // when
        mockMvc.perform(post("/fest/review")
                        .header("memSeq", 1L)
                .content(objectMapper.writeValueAsString(addReviewReq))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        // then
        BDDMockito.verify(reviewService).addReview(anyLong(), any(AddReviewReq.class));
    }

    @Nested
    @DisplayName("리뷰 등록 실패 - InvalidRequest")
    class addReview_InvalidRequest {

        private AddReviewReq addReviewReq;
        @Test
        @DisplayName("invalid content empty")
        void addReview_content_empty() throws Exception {
            addReviewReq = new AddReviewReq(null, 3, List.of(""), List.of(3), 6L);
            perform();
        }

        @Test
        @DisplayName("invalid point under range")
        void addReview_point_under() throws Exception {
            addReviewReq = new AddReviewReq("정말 좋음", -1, List.of(""), List.of(3), 6L);
            perform();
        }

        @Test
        @DisplayName("invalid point over range")
        void addReview_point_over() throws Exception {
            addReviewReq = new AddReviewReq("정말 좋음", 6, List.of(""), List.of(3), 6L);
            perform();
        }

        private void perform() throws Exception {
            // when
            mockMvc.perform(post("/fest/review")
                            .header("memSeq", 1L)
                            .content(objectMapper.writeValueAsString(addReviewReq))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

    }

}