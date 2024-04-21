package com.seouldata.auth.global.exception;

import com.seouldata.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    INVALID_TOKEN(300, "토큰이 유효하지 않습니다."),

    USER_NOT_FOUND(300, "사용자를 찾을 수 없습니다."),
    USER_ALREADY_EXISTS(500, "이미 가입된 사용자입니다."),

    NICKNAME_ALREADY_EXISTS(500, "이미 사용중인 닉네임입니다."),

    BADGE_NOT_FOUND(500, "뱃지를 찾을 수 없습니다.")
    ;

    private final int code;
    private final String message;

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
