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
        String nearestHour = getNearestHour(localDateTime);
        String minute = "00";
        return nearestHour + minute;
    }

    /**
     * LIVE, SHORT = 0500(6~8) 0800(6~7) 1700 2000
     */
    private String getNearestHour(LocalDateTime dateTime) {
        if (dateTime.getHour() > 5 && dateTime.getHour() < 9) {
            return "05";
        } else if (dateTime.getHour() > 8 && dateTime.getHour() < 18) {
            return "08";
        } else if (dateTime.getHour() > 17 && dateTime.getHour() < 21) {
            return "17";
        } else {
            return "20";
        }
    }
}
