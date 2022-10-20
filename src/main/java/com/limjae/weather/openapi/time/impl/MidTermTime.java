package com.limjae.weather.openapi.time.impl;

import com.limjae.weather.openapi.time.OpenApiTime;
import com.limjae.weather.openapi.type.OpenApiType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MidTermTime implements OpenApiTime {

    @Override
    public OpenApiType getType() {
        return OpenApiType.MID_TERM;
    }

    @Override
    public String getDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.BASIC_ISO_DATE);
    }

    @Override
    public String getBaseHourAndMinute(LocalDateTime dateTime) {
        String nearestHour = getNearestHour(dateTime);
        String minute = "00";
        return nearestHour + minute;
    }

    /**
     * MIDTERM = 0600, 1800
     */
    private String getNearestHour(LocalDateTime dateTime) {
        if (dateTime.getHour() < 13) {
            return "06";
        } else {
            return "18";
        }
    }
}
