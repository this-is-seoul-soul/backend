package com.seouldata.fest.domain.entity;

public enum Codename {

    MUSICAL_OPERA("뮤지컬/오페라", 1),
    CLASSIC("클래식", 2),
    TRADITION_MUSIC("국악", 3),
    THEATER("연극", 4),
    DANCING("무용", 5),
    EXHIBITION_ART("전시/미술", 6),
    EDUCATION_EXPERIENCE("교육/체험", 7),
    CONCERT("콘서트", 8),
    SOLO_RECITAL("독주/독창회", 9),
    MOVIE("영화", 10),
    ETC("기타", 11),
    FEST_CIVIL("축제-시민화합", 12),
    FEST_TRADITION_HISTORY("축제-전통/역사", 13),
    FEST_CULTURE_ART("축제-문화/예술", 14),
    FEST_ETC("축제-기타", 15),
    FEST_NATURE_LANDSCAPE("축제-자연/경관", 16);

    private final String codeType;
    private final int codeNum;

    Codename(String codeType, int codeNum) {
        this.codeType = codeType;
        this.codeNum = codeNum;
    }

    public int getCodeNum() {
        return codeNum;
    }

}
