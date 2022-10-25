package com.limjae.weather.openapi.time.impl;

import com.limjae.weather.openapi.time.OpenApiTime;
import com.limjae.weather.openapi.type.OpenApiType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ShortTime implements OpenApiTime {
    @Override
    public OpenApiType getType() {
        return OpenApiType.SHORT;
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
     * LIVE, SHORT = 0500(6~8) 0800(6~7) 1700 2000
     */
    private String getBaseHour(LocalDateTime dateTime) {
        return switch (dateTime.getHour()) {
            case 6, 7, 8 -> "05";
            case 9, 10, 11 -> "08";
            case 12, 13, 14, 15, 16, 17 -> "11";
            case 18, 19, 20 -> "17";
            case 21, 22, 23 -> "20";
            default -> throw new IllegalArgumentException("잘못된 시간입니다. :" + dateTime);
        };
    }
}
