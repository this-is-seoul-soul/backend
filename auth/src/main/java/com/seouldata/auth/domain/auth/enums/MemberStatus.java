package com.seouldata.auth.domain.auth.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberStatus {

    INIT("init", "비회원 상태"),
    NICKNAME("nickname", "닉네임 설정 필요"),
    FESTI("festi", "festI 설정 필요"),
    COMPLETE("complete", "회원 가입 완료")
    ;

    private final String label;
    private final String description;
}
