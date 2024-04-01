package com.seouldata.fest.domain.heart.service;

public interface HeartService {

    void addHeart(Long memSeq, Long festSeq);

    void removeHeart(Long heartSeq);

}
