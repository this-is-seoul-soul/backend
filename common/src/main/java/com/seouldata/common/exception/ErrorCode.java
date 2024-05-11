package com.seouldata.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // User 관련
    UNAUTHORIZED_USER(403, "권한이 없는 사용자입니다."),
    INVALID_TOKEN(300, "토큰이 유효하지 않습니다."),
    USER_NOT_FOUND(300, "사용자를 찾을 수 없습니다."),
    USER_ALREADY_EXISTS(500, "이미 가입된 사용자입니다."),
    NICKNAME_ALREADY_EXISTS(500, "이미 사용중인 닉네임입니다."),

    // Fest 관련
    FEST_NOT_FOUND(400, "축제 정보가 없습니다."),
    INVALID_CODE_NAME(400, "유효하지 않은 코드명입니다."),
    FAIL_MEMBER_INFO(400, "memSeq를 받아오지 못했습니다."),

    // Review 관련
    REVIEW_NOT_FOUND(400, "해당 리뷰가 없습니다."),
    SEARCH_OPTION_INVALID(400, "검색 조건이 잘못되었습니다."),

    // Heart 관련
    HEART_NOT_FOUND(400, "찜한 내역이 없습니다"),

    // Badge 관련
    BADGE_NOT_FOUND(500, "뱃지를 찾을 수 없습니다.")
    ;


    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
