package com.limjae.weather.openapi.uri;

import com.limjae.weather.entity.enums.LocationEnum;
import com.limjae.weather.openapi.time.OpenApiTime;
import com.limjae.weather.openapi.type.OpenApiType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OpenApiParameter {
    private final String date;
    private final String time;
    private final OpenApiType type;
    private final LocationEnum location;

    public OpenApiParameter(OpenApiTime openApiTime, LocalDateTime localDateTime, LocationEnum location) {
        this.date = openApiTime.getDate(localDateTime);
        this.time = openApiTime.getBaseHourAndMinute(localDateTime);
        this.type = openApiTime.getType();
        this.location = location;
    }
}
