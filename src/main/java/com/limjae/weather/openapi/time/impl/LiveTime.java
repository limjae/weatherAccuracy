package com.limjae.weather.openapi.time.impl;

import com.limjae.weather.openapi.time.OpenApiTime;
import com.limjae.weather.openapi.type.OpenApiType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LiveTime implements OpenApiTime {
    @Override
    public OpenApiType getType() {
        return OpenApiType.LIVE;
    }

    @Override
    public String getDate(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.BASIC_ISO_DATE);
    }

    @Override
    public String getBaseHourAndMinute(LocalDateTime localDateTime) {
        String nearestHour = getBaseHour(localDateTime);
        String minute = "00";
        return nearestHour + minute;
    }

    /**
     * LIVE = 5,6,8, 11, 17,18,20
     */
    private String getBaseHour(LocalDateTime dateTime) {
        return switch (dateTime.getHour()) {
            case 6 -> "05";
            case 7, 8 -> "06";
            case 9, 10, 11 -> "08";
            case 12, 13, 14, 15, 16, 17 -> "11";
            case 18 -> "17";
            case 19, 20 -> "18";
            case 21, 22, 23 -> "20";
            default -> throw new IllegalArgumentException("잘못된 시간입니다. :" + dateTime);
        };
    }
}
