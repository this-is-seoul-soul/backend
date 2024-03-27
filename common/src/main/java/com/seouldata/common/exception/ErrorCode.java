package com.seouldata.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    FEST_NOT_FOUND(400, "축제 정보가 없습니다."),
    REVIEW_NOT_FOUND(400, "해당 리뷰가 없습니다.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
