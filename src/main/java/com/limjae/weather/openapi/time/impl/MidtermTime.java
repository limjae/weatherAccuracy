package com.limjae.weather.openapi.time.impl;

import com.limjae.weather.openapi.time.OpenApiTime;
import com.limjae.weather.openapi.type.OpenApiType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MidtermTime implements OpenApiTime {

    @Override
    public OpenApiType getType() {
        return OpenApiType.MIDTERM;
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
     * MIDTERM = 0600, 1800
     */
    private String getBaseHour(LocalDateTime dateTime) {
        if (dateTime.getHour() > 6 && dateTime.getHour() < 19) {
            return "06";
        } else {
            return "18";
        }
    }
}
