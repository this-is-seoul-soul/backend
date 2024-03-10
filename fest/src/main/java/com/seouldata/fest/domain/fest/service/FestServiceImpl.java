package com.seouldata.fest.domain.fest.service;

import com.seouldata.fest.domain.fest.client.OpenApiFeignClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FestServiceImpl implements FestService{

    private final OpenApiFeignClient openApiFeignClient;

    @Scheduled(cron = "00 00 21 * * ?", zone = "Asia/Seoul")
    public void getFestData() {
        openApiFeignClient.getFestList();
    }

}
