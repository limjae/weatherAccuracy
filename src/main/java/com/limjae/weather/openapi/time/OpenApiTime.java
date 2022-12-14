package com.limjae.weather.openapi.time;

import com.limjae.weather.openapi.type.OpenApiType;

import java.time.LocalDateTime;

public interface OpenApiTime {
    public OpenApiType getType();
    public String getDate(LocalDateTime localDateTime);
    public String getBaseHourAndMinute(LocalDateTime localDateTime);
}
