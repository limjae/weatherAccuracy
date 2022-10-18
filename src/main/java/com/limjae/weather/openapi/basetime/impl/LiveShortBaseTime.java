package com.limjae.weather.openapi.basetime.impl;

import com.limjae.weather.openapi.basetime.OpenApiBaseTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LiveShortBaseTime implements OpenApiBaseTime {
    private final LocalDateTime dateTime;

    public LiveShortBaseTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDate() {
        return dateTime.format(DateTimeFormatter.BASIC_ISO_DATE);
    }

    public String getBaseHourAndMinute() {
        String nearestHour = getNearestHour();
        String minute = "00";
        return nearestHour + minute;
    }

    /**
     * LIVE, SHORT = 0500(6~8) 0800(6~7) 1700 2000
     */
    private String getNearestHour() {
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
