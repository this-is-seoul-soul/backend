package com.seouldata.fest.domain.fest.entity;

import lombok.Getter;

public enum Codename {

    MUSICAL_OPERA("뮤지컬/오페라", 1, "SACE"),
    CLASSIC("클래식", 2, "SACI"),
    TRADITION_MUSIC("국악", 3, "SACE"),
    THEATER("연극", 4, "SVCE"),
    DANCING("무용", 5, "SVCE"),
    EXHIBITION_ART("전시/미술", 6, "SVCI"),
    EDUCATION_EXPERIENCE("교육/체험", 7, "DVTE"),
    CONCERT("콘서트", 8, "SACE"),
    SOLO_RECITAL("독주/독창회", 9, "SATE"),
    MOVIE("영화", 10, "SVCI"),
    ETC("기타", 11, "XXXX"),
    FEST_CIVIL("축제-시민화합", 12, "DVCE"),
    FEST_TRADITION_HISTORY("축제-전통/역사", 13, "DVCE"),
    FEST_CULTURE_ART("축제-문화/예술", 14, "DVCE"),
    FEST_ETC("축제-기타", 15, "XXXX"),
    FEST_NATURE_LANDSCAPE("축제-자연/경관", 16, "DVCE");


    @Getter
    private final String codeType;
    @Getter
    private final int codeNum;
    @Getter
    private final String festi;

    Codename(String codeType, int codeNum, String festi) {
        this.codeType = codeType;
        this.codeNum = codeNum;
        this.festi = festi;
    }

    public static int getCodeNum(String codeType) {

        for (Codename codename : Codename.values()) {
            if (codename.codeType.equals(codeType)) {
                return codename.codeNum;
            }
        }

        return -1;
    }

    public static String getCodeType(int codeNum) {

        for (Codename codename : Codename.values()) {
            if (codename.codeNum == codeNum) {
                return codename.codeType;
            }
        }

        return ETC.codeType;
    }

}
