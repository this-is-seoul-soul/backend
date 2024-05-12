package com.seouldata.fest.domain.heart.service;

import com.seouldata.common.exception.BusinessException;
import com.seouldata.common.exception.ErrorCode;
import com.seouldata.fest.domain.fest.entity.Codename;
import com.seouldata.fest.domain.fest.entity.Fest;
import com.seouldata.fest.domain.fest.repository.FestRepository;
import com.seouldata.fest.domain.heart.dto.response.GetHeartRes;
import com.seouldata.fest.domain.heart.entity.Heart;
import com.seouldata.fest.domain.heart.repository.HeartRepository;
import com.seouldata.fest.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService{

    private final HeartRepository heartRepository;
    private final FestRepository festRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public void addHeart(Long memSeq, Long festSeq) {

        Fest fest = festRepository.findByFestSeq(festSeq)
                        .orElseThrow(() -> new BusinessException(ErrorCode.FEST_NOT_FOUND));

        if (heartRepository.existsByFestAndAndMemSeq(fest, memSeq)) {
            throw new BusinessException(ErrorCode.HEART_ALREADY_EXIST);
        }

        heartRepository.save(Heart.builder()
                .memSeq(memSeq)
                .fest(fest)
                .build());
    }

    @Override
    public void removeHeart(Long memSeq, Long festSeq) {

        Fest fest = festRepository.findByFestSeq(festSeq)
                .orElseThrow(() -> new BusinessException(ErrorCode.FEST_NOT_FOUND));

        Heart heart = heartRepository.findByMemSeqAndFest(memSeq, fest)
                .orElseThrow(() -> new BusinessException(ErrorCode.HEART_NOT_FOUND));

        heartRepository.delete(heart);
    }

    @Override
    public List<GetHeartRes> getHeart(Long memSeq) {

        List<Fest> festList = heartRepository.findFestByMemSeq(memSeq);

        return festList.stream()
                .map(fest -> {
                    LocalDateTime today = LocalDateTime.now();
                    boolean isContinue = !fest.getStartDate().isAfter(today) && !fest.getEndDate().isBefore(today);
                    Double avgPoint = reviewRepository.findPointByMemSeqAndFest(memSeq, fest);
                    return GetHeartRes.builder()
                            .festSeq(fest.getFestSeq())
                            .title(fest.getTitle())
                            .codeName(Codename.getCodeType(fest.getCodename()))
                            .mainImg(fest.getMainImg())
                            .startDate(fest.getStartDate())
                            .endDate(fest.getEndDate())
                            .useFee(fest.getUseFee())
                            .avgPoint(avgPoint == null ? 0.0 : avgPoint)
                            .cntReview(reviewRepository.countAllByMemSeqAndFestAndDeletedIsFalse(memSeq, fest))
                            .isContinue(isContinue)
                            .isHeart(true)
                            .build();
                }).collect(Collectors.toList());
    }

}
