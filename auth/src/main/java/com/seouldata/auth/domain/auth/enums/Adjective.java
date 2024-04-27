package com.seouldata.auth.domain.auth.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Adjective {

    HAPPY ("기분 좋은"),
    SAD ("슬픈"),
    EXCITED ("신이 난"),
    ANGRY ("화난"),
    CALM ("차분한"),
    JOYFUL ("기쁜"),
    GLOOMY ("우울한"),
    CONTENT ("만족스러운"),
    ENTHUSIASTIC ("열정적인"),
    RELAXED ("편안한"),
    NERVOUS ("긴장하는"),
    SURPRISED ("놀란"),
    CONFUSED ("혼란스러운"),
    PROUD ("자랑스러운"),
    SCARED ("무서워하는"),
    HOPEFUL ("희망찬"),
    GRATEFUL ("감사한"),
    INSECURE ("불안한"),
    JEALOUS ("질투하는"),
    LONELY ("외로운"),
    DISAPPOINTED ("실망한"),
    TIRED ("피곤한"),
    ENERGETIC ("에너지 넘치는"),
    AMUSED ("극도로 감탄하는"),
    CURIOUS ("호기심 많은"),
    RELIEVED ("안심한"),
    ANTICIPATORY ("기대하는"),
    AWE_STRUCK ("경탄하는"),
    APPREHENSIVE ("걱정스러운"),
    ;

    private final String korean;

    public String getKorean() { return this.korean; }

}