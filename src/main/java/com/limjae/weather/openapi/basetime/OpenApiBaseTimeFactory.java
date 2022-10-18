package com.limjae.weather.openapi.basetime;

import com.limjae.weather.openapi.basetime.impl.LiveShortBaseTime;
import com.limjae.weather.openapi.type.OpenApiType;

import java.time.LocalDateTime;

public class OpenApiBaseTimeFactory {

    public static OpenApiBaseTime getOpenApiBaseTime(OpenApiType type, LocalDateTime dateTime) {
        return switch (type) {
            case LIVE, SHORT_TERM -> new LiveShortBaseTime(dateTime);
//            case LIVE, SHORT -> new LiveShortBaseTime();
            default -> throw new RuntimeException("Invalid Type for generate OpenApiBaseTime");
        };
    }
}
