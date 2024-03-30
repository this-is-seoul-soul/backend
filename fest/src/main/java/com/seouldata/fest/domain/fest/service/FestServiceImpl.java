package com.seouldata.fest.domain.fest.service;

import com.seouldata.fest.domain.fest.client.OpenApiFeignClient;
import com.seouldata.fest.domain.fest.dto.request.AddFestReq;
import com.seouldata.fest.domain.fest.dto.response.GetFestResDto;
import com.seouldata.fest.domain.fest.entity.Codename;
import com.seouldata.fest.domain.fest.entity.Fest;
import com.seouldata.fest.domain.fest.repository.FestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FestServiceImpl implements FestService {

    private final OpenApiFeignClient openApiFeignClient;

    private final FestRepository festRepository;

    @Override
    @Scheduled(cron = "00 00 21 * * ?", zone = "Asia/Seoul")
    public void getFestData() {

        long firstFestIdx = festRepository.countAllByIsPublic(true) + 1;
        int lastFestIdx = openApiFeignClient.getFestCnt().getCulturalEventInfo().getListTotalCount();

        if(firstFestIdx >= lastFestIdx)
            return;

        boolean stopFlag = false;
        while (!stopFlag) {

            String path;
            if (lastFestIdx - firstFestIdx < 1000) {
                path = firstFestIdx + "/" + lastFestIdx;
                stopFlag = true;
            } else {
                path = firstFestIdx + "/" + (firstFestIdx + 999);
                firstFestIdx += 1000;
            }

            log.debug("path: {}", path);

            GetFestResDto getFestResDto = openApiFeignClient.getFestList(path);

            for (GetFestResDto.Row row : getFestResDto.getCulturalEventInfo().getRow()) {

                double lot, lat;
                try {
                    lot = Double.parseDouble(row.getLot());
                    lat = Double.parseDouble(row.getLat());
                } catch (NumberFormatException e) {
                    lot = 0;
                    lat = 0;
                }

                festRepository.save(
                        Fest.builder()
                                .title(row.getTitle())
                                .codename(Codename.getCodeNum(row.getCodename()))
                                .guname(row.getGuname())
                                .place(row.getOrgName())
                                .useTrgt(row.getUseTrgt())
                                .isFree(row.getIS_FREE())
                                .useFee(row.getUseFee())
                                .startDate(row.getStartDate())
                                .endDate(row.getEndDate())
                                .lot(lot)
                                .lat(lat)
                                .orgLink(row.getOrgLink())
                                .mainImg(row.getMainImg())
                                .isPublic(true)
                                .build()
                );

            }

        }

    }

    @Override
    public void addFest(Long memSeq, AddFestReq addFestReq) {

        festRepository.save(
                Fest.builder()
                        .title(addFestReq.getTitle())
                        .codename(Codename.getCodeNum(addFestReq.getCodeName()))
                        .guname(addFestReq.getGuName())
                        .place(addFestReq.getPlace())
                        .useTrgt(addFestReq.getUseTrgt())
                        .isFree(addFestReq.getIsFree())
                        .useFee(addFestReq.getUseFee())
                        .startDate(addFestReq.getStartDate().atStartOfDay())
                        .endDate(addFestReq.getEndDate().atStartOfDay())
                        .lot(addFestReq.getLot())
                        .lat(addFestReq.getLat())
                        .orgLink(addFestReq.getOrgLink())
                        .mainImg(addFestReq.getMainImg())
                        .isPublic(false)
                        .creator(memSeq)
                        .build()
        );

    }

}
