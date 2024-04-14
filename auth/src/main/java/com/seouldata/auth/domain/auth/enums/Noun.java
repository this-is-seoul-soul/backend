package com.seouldata.auth.domain.auth.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Noun {

    DOG ("개"),
    CAT ("고양이"),
    MOUSE ("쥐"),
    ELEPHANT ("코끼리"),
    MONKEY ("원숭이"),
    TIGER ("호랑이"),
    LION ("사자"),
    BEAR ("곰"),
    DEER ("사슴"),
    SQUIRREL ("다람쥐"),
    RABBIT ("토끼"),
    SNAKE ("뱀"),
    HORSE ("말"),
    COW ("소"),
    PIG ("돼지"),
    SHEEP ("양"),
    PEACOCK ("공작새"),
    PIGEON ("비둘기"),
    WHALE ("고래"),
    SHARK ("상어"),
    DOLPHIN ("돌고래"),
    PENGUIN ("팽귄"),
    RHINO ("코뿔소"),
    CROCODILE ("악어"),
    DUCK ("오리"),
    DINOSAUR ("공룡"),
    CAMEL ("낙타"),
    RACCOON ("오소리"),
    RED_PANDA ("랫서팬더"),
    KANGAROO ("캥거루"),
    ;

    private final String korean;

    public String getKorean() { return this.korean; }

}
