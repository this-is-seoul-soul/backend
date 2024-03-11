package com.seouldata.fest.domain.repository;

import com.seouldata.fest.domain.fest.entity.Fest;
import com.seouldata.fest.domain.fest.repository.FestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class FestRepositoryTest {

    @Autowired
    private FestRepository festRepository;

    @Test
    public void InsertDummy() {

        Fest fest = Fest.builder()
                .title("오페라 갈라")
                .codename(1)
                .guname("종로구")
                .place("세종대극장")
                .useTrgt("7세 이상 관람 가능(2017년생부터 관람 가능)")
                .isFree("유료")
                .useFee("SUITE석 140,000원 / VIP석 120,000원 / R석 100,000원 / S석 80,000원 / A석 50,000원 / B석 30,000원")
                .startDate(LocalDateTime.of(2024, 1, 29, 0, 0))
                .endDate(LocalDateTime.of(2024, 1, 29, 0, 0))
                .lot(37.5726241)
                .lat(126.9760053)
                .orgLink("https://www.sejongpac.or.kr/portal/performance/performance/view.do?menuNo=200004&performIdx=34913")
                .mainImg("https://culture.seoul.go.kr/cmmn/file/getImage.do?atchFileId=ffee77c39b7f4fa793316e56fad4dc63&thumb=Y")
                .build();

        festRepository.save(fest);
    }

}
