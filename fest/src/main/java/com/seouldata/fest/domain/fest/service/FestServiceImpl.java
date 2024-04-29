package com.seouldata.fest.domain.fest.service;

import com.seouldata.common.exception.BusinessException;
import com.seouldata.common.exception.ErrorCode;
import com.seouldata.fest.domain.fest.client.OpenApiFeignClient;
import com.seouldata.fest.domain.fest.dto.request.AddFestReq;
import com.seouldata.fest.domain.fest.dto.request.FindByCodeReq;
import com.seouldata.fest.domain.fest.dto.request.FindFestByCriteriaReq;
import com.seouldata.fest.domain.fest.dto.request.ModifyFestReq;
import com.seouldata.fest.domain.fest.dto.response.*;
import com.seouldata.fest.domain.fest.entity.Codename;
import com.seouldata.fest.domain.fest.entity.Fest;
import com.seouldata.fest.domain.fest.repository.FestRepository;
import com.seouldata.fest.domain.review.dto.response.GetMemberInfoRes;
import com.seouldata.fest.domain.review.repository.ReviewRepository;
import com.seouldata.fest.domain.review.util.InfoUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FestServiceImpl implements FestService {

    private final OpenApiFeignClient openApiFeignClient;

    private final FestRepository festRepository;
    private final ReviewRepository reviewRepository;

    private final InfoUtil infoUtil;

    @Override
    @Scheduled(cron = "00 00 21 * * ?", zone = "Asia/Seoul")
    public void getFestData() {

        long firstFestIdx = festRepository.countAllByIsPublic(true) + 1;
        int lastFestIdx = openApiFeignClient.getFestCnt().getCulturalEventInfo().getListTotalCount();

        if (firstFestIdx >= lastFestIdx)
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

    @Override
    public void updateFest(Long memSeq, ModifyFestReq modifyFestReq) {

        Fest fest = festRepository.findByFestSeq(modifyFestReq.getFestSeq())
                .orElseThrow(() -> new BusinessException(ErrorCode.FEST_NOT_FOUND));

        if (!Objects.equals(memSeq, fest.getCreator()))
            throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);

        int codename = Codename.getCodeNum(modifyFestReq.getCodeName());
        if (codename == -1)
            throw new BusinessException(ErrorCode.INVALID_CODE_NAME);

        fest.modify(
                modifyFestReq.getTitle(),
                codename,
                modifyFestReq.getGuName(),
                modifyFestReq.getPlace(),
                modifyFestReq.getUseTrgt(),
                modifyFestReq.getIsFree(),
                modifyFestReq.getUseFee(),
                modifyFestReq.getStartDate().atStartOfDay(),
                modifyFestReq.getEndDate().atStartOfDay(),
                modifyFestReq.getLot(),
                modifyFestReq.getLat(),
                modifyFestReq.getOrgLink(),
                modifyFestReq.getMainImg()
        );

    }

    @Override
    public void removeFest(Long memSeq, Long festSeq) {

        Fest fest = festRepository.findByFestSeq(festSeq)
                .orElseThrow(() -> new BusinessException(ErrorCode.FEST_NOT_FOUND));

        if (!Objects.equals(memSeq, fest.getCreator()))
            throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);

        fest.remove();

    }

    @Override
    public GetFestDetailRes getFestDetail(Long memSeq, Long festSeq) {

        Fest fest = festRepository.findByFestSeq(festSeq)
                .orElseThrow(() -> new BusinessException(ErrorCode.FEST_NOT_FOUND));

        Double avgPoint = reviewRepository.findPointByFest(fest);
        int cntReview = reviewRepository.countAllByFestAndDeletedIsFalse(fest);

        return GetFestDetailRes.builder()
                .festSeq(fest.getFestSeq())
                .title(fest.getTitle())
                .codename(Codename.getCodeType(fest.getCodename()))
                .guname(fest.getGuname())
                .place(fest.getPlace())
                .useTrgt(fest.getUseTrgt())
                .isFree(fest.getIsFree())
                .useFee(fest.getUseFee())
                .startDate(fest.getStartDate())
                .endDate(fest.getEndDate())
                .lot(fest.getLot())
                .lat(fest.getLat())
                .orgLink(fest.getOrgLink())
                .mainImg(fest.getMainImg())
                .avgPoint(avgPoint == null ? 0.0 : avgPoint)
                .cntReview(cntReview)
                .isContinue(fest.getStartDate().isBefore(LocalDateTime.now()) && fest.getEndDate().isAfter(LocalDateTime.now()))
                .isHeart(festRepository.findHeartByMemSeqAndFestSeq(memSeq, festSeq))
                .build();
    }

    @Override
    public List<GetFestRes> getFestByCode(Long memSeq, FindByCodeReq findByCodeReq) {
        List<Fest> festList = festRepository.findByCodeWithCursor(findByCodeReq, findByCodeReq.getSort(), PageRequest.of(findByCodeReq.getPage(), findByCodeReq.getLimit()));

        return festList.stream()
                .map(fest -> {

                    Double avgPoint = reviewRepository.findPointByFest(fest);
                    int cntReview = reviewRepository.countAllByFestAndDeletedIsFalse(fest);

                    return GetFestRes.builder()
                            .festSeq(fest.getFestSeq())
                            .title(fest.getTitle())
                            .codename(findByCodeReq.getCodename())
                            .mainImg(fest.getMainImg())
                            .startDate(fest.getStartDate())
                            .endDate(fest.getEndDate())
                            .useFee(fest.getUseFee())
                            .avgPoint(avgPoint == null ? 0.0 : avgPoint)
                            .cntReview(cntReview)
                            .isContinue(findByCodeReq.isContinue() ? true : fest.getStartDate().isBefore(LocalDateTime.now()) && fest.getEndDate().isAfter(LocalDateTime.now()))
                            .isHeart(festRepository.findHeartByMemSeqAndFestSeq(memSeq, fest.getFestSeq()))
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<GetFestByCriteriaResDto> getFestByCriteria(Long memSeq, FindFestByCriteriaReq findFestByCriteriaReq) {

        return festRepository.findAllByCriteria(memSeq, findFestByCriteriaReq);
    }

    @Override
    public List<GetFestRes> getFestList(Long memSeq, String keyword, double lot, double lat) {

        List<Fest> festList;

        if (lat == -1 && lot == -1)
            festList = festRepository.findByKeyword(keyword);

        else
            festList = festRepository.findByKeywordAndLocation(keyword, lot, lat);

        return festList.stream()
                .map(fest -> {

                    Double avgPoint = reviewRepository.findPointByFest(fest);
                    int cntReview = reviewRepository.countAllByFestAndDeletedIsFalse(fest);

                    return GetFestRes.builder()
                            .festSeq(fest.getFestSeq())
                            .title(fest.getTitle())
                            .codename(Codename.getCodeType(fest.getCodename()))
                            .mainImg(fest.getMainImg())
                            .startDate(fest.getStartDate())
                            .endDate(fest.getEndDate())
                            .useFee(fest.getUseFee())
                            .avgPoint(avgPoint == null ? 0.0 : avgPoint)
                            .cntReview(cntReview)
                            .isContinue(fest.getStartDate().isBefore(LocalDateTime.now()) && fest.getEndDate().isAfter(LocalDateTime.now()))
                            .isHeart(festRepository.findHeartByMemSeqAndFestSeq(memSeq, fest.getFestSeq()))
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<GetFestDetailRes> getRecommendFest(Long memSeq) {

        GetMemberInfoRes creatInfo = infoUtil.getMemberInfo(memSeq);
        String mbti = creatInfo.getMbti();

        List<Integer> festCoodenumList = new ArrayList<>();

        for (Codename codename : Codename.values()) {
            if (codename.getFesti().equals("XXXX")) {
                festCoodenumList.add(codename.getCodeNum());
            } else {
                int matchCnt = 0;
                for (int i = 0; i < 4; i++) {
                    if (mbti.charAt(i) == codename.getFesti().charAt(i))
                        matchCnt++;
                }
                if (matchCnt >= 3)
                    festCoodenumList.add(codename.getCodeNum());
            }
        }

        List<Fest> festList = festRepository.findTop10ByCodenameInOrderByFestSeqDesc(festCoodenumList);

        return festList.stream()
                .map(fest -> {

                    Double avgPoint = reviewRepository.findPointByFest(fest);
                    int cntReview = reviewRepository.countAllByFestAndDeletedIsFalse(fest);

                    return GetFestDetailRes.builder()
                            .festSeq(fest.getFestSeq())
                            .title(fest.getTitle())
                            .codename(Codename.getCodeType(fest.getCodename()))
                            .guname(fest.getGuname())
                            .place(fest.getPlace())
                            .useTrgt(fest.getUseTrgt())
                            .isFree(fest.getIsFree())
                            .useFee(fest.getUseFee())
                            .startDate(fest.getStartDate())
                            .endDate(fest.getEndDate())
                            .lot(fest.getLot())
                            .lat(fest.getLat())
                            .orgLink(fest.getOrgLink())
                            .mainImg(fest.getMainImg())
                            .avgPoint(avgPoint == null ? 0.0 : avgPoint)
                            .cntReview(cntReview)
                            .isContinue(fest.getStartDate().isBefore(LocalDateTime.now()) && fest.getEndDate().isAfter(LocalDateTime.now()))
                            .isHeart(festRepository.findHeartByMemSeqAndFestSeq(memSeq, fest.getFestSeq()))
                            .build();
                })
                .collect(Collectors.toList());
    }

}
