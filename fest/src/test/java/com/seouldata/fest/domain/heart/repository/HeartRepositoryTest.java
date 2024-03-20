package com.seouldata.fest.domain.heart.repository;

import com.seouldata.fest.domain.fest.repository.FestRepository;
import com.seouldata.fest.domain.heart.entity.Heart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class HeartRepositoryTest {

    @Autowired
    private FestRepository festRepository;

    @Autowired
    private HeartRepository heartRepository;

    @Test
    public void InsertHeartDummy() {

        Heart heart = Heart.builder()
                .memSeq(1L)
                .fest(festRepository.findById(1L).get())
                .build();

        heartRepository.save(heart);
    }

}
