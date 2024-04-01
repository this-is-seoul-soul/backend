package com.seouldata.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // User 관련
    UNAUTHORIZED_USER(403, "권한이 없는 사용자입니다."),

    // Fest 관련
    FEST_NOT_FOUND(400, "축제 정보가 없습니다."),
    INVALID_CODE_NAME(400, "유효하지 않은 코드명입니다."),

    // Review 관련
    REVIEW_NOT_FOUND(400, "해당 리뷰가 없습니다."),

    // Heart 관련
    HEART_NOT_FOUND(400, "찜한 내역이 없습니다");


    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
