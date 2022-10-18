package com.limjae.weather.openapi.basetime.impl;

import com.limjae.weather.openapi.basetime.OpenApiBaseTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MidTermBaseTime implements OpenApiBaseTime {
    private final LocalDateTime dateTime;

    public MidTermBaseTime(LocalDateTime dateTime) {
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
     * MIDTERM = 0600, 1800
     */
    private String getNearestHour() {
        if (dateTime.getHour() < 13) {
            return "06";
        } else {
            return "18";
        }
    }
}
