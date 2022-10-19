package com.limjae.weather.openapi.uri;

import com.limjae.weather.openapi.basetime.OpenApiBaseTime;
import com.limjae.weather.openapi.type.OpenApiType;
import com.limjae.weather.openapi.uri.impl.LiveForecastApiUri;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

public class OpenApiUriFactory {

    public static OpenApiUri generate(OpenApiType type, OpenApiBaseTime baseTime) {
        return switch (type) {
            case LIVE, SHORT_TERM -> new LiveForecastApiUri();
//            case LIVE, SHORT -> new LiveShortBaseTime();
            default -> throw new RuntimeException("Invalid Type for generate OpenApiBaseUri");
        };
    }
}
