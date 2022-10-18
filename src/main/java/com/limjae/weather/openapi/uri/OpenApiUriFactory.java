package com.limjae.weather.openapi.uri;

import com.limjae.weather.openapi.type.OpenApiType;
import com.limjae.weather.openapi.uri.impl.LiveForecastURI;

import java.time.LocalDateTime;
public class OpenApiUriFactory {

    public static OpenApiUri getOpenApiUri(OpenApiType type, LocalDateTime dateTime) {
        return switch (type) {
            case LIVE, SHORT_TERM -> new LiveForecastURI();
//            case LIVE, SHORT -> new LiveShortBaseTime();
            default -> throw new RuntimeException("Invalid Type for generate OpenApiBaseUri");
        };
    }
}
