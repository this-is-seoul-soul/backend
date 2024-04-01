package com.seouldata.fest.domain.heart.service;

import com.seouldata.common.exception.BusinessException;
import com.seouldata.common.exception.ErrorCode;
import com.seouldata.fest.domain.fest.entity.Fest;
import com.seouldata.fest.domain.fest.repository.FestRepository;
import com.seouldata.fest.domain.heart.entity.Heart;
import com.seouldata.fest.domain.heart.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService{

    private final HeartRepository heartRepository;
    private final FestRepository festRepository;

    @Override
    public void addHeart(Long memSeq, Long festSeq) {

        Fest fest = festRepository.findByFestSeq(festSeq)
                        .orElseThrow(() -> new BusinessException(ErrorCode.FEST_NOT_FOUND));

        heartRepository.save(Heart.builder()
                .memSeq(memSeq)
                .fest(fest)
                .build());
    }

    @Override
    public void removeHeart(Long heartSeq) {

        Heart heart = heartRepository.findByHeartSeq(heartSeq)
                .orElseThrow(() -> new BusinessException(ErrorCode.HEART_NOT_FOUND));

        heartRepository.delete(heart);
    }
}
