package com.limjae.weather.entity.enums;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum RainEnum {
    ERROR(-1, "정보없음/식별불가"),
    NONE(0, "없음"),
    RAIN(1, "비"),
    RAIN_AND_SNOW(2, "비/눈"),
    SNOW(3, "눈"),
    DROPS(5, "빗방울"),
    DROPS_AND_SNOW_DRIFTING(6, "빗방울/눈날림"),
    SNOW_DRIFTING(7, "눈날림"),
    ;

    private final int code;
    private final String statusMsg;

    private static final Map<Integer, RainEnum> BY_CODE =
            Stream.of(values()).collect(Collectors.toMap(RainEnum::code, Function.identity()));

    RainEnum(int code, String message) {
        this.code = code;
        this.statusMsg = message;
    }

    public int code() {
        return code;
    }

    public String statusMsg() {
        return statusMsg;
    }

    public static RainEnum valueOfCode(int code) {
        return BY_CODE.getOrDefault(code, RainEnum.ERROR);
    }


}
