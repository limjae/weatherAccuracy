package com.limjae.weather.openapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.limjae.weather.entity._global.Weather;
import com.limjae.weather.entity.enums.LocationEnum;
import com.limjae.weather.openapi.dto.CommonApiResponseDto;
import com.limjae.weather.openapi.parser.OpenApiParser;
import com.limjae.weather.openapi.parser.OpenApiParserFactory;
import com.limjae.weather.openapi.time.OpenApiTime;
import com.limjae.weather.openapi.time.OpenApiTimeFactory;
import com.limjae.weather.openapi.type.OpenApiType;
import com.limjae.weather.openapi.uri.OpenApiParameter;
import com.limjae.weather.openapi.uri.OpenApiUri;
import com.limjae.weather.openapi.uri.OpenApiUriFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ## 새로운 종류의 Api를 만들려면 OpenApiType에 타입을 추가하고 다음 인터페이스를 구현해야합니다. ##
 * @see OpenApiType
 * @see OpenApiParser
 * @see OpenApiTime
 * @see OpenApiUri
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OpenApi {
    private final OpenApiTimeFactory baseTimeFactory;
    private final OpenApiUriFactory uriFactory;
    private final OpenApiParserFactory parserFactory;

    public Weather load(OpenApiType type, LocalDateTime localDateTime, int predictionDay, LocationEnum location) {
        OpenApiTime apiTime = baseTimeFactory.generate(type);
        OpenApiUri openApiUri = uriFactory.generate(type);
        OpenApiParameter openApiParameter = new OpenApiParameter(apiTime, localDateTime, location);
        URI uri = openApiUri.getURI(openApiParameter);

        return connect(type, uri, predictionDay);
    }

    public List<Weather> loadAllLocation(OpenApiType type, LocalDateTime localDateTime, int predictionDay) {
        List<Weather> result = new ArrayList<>();

        for (LocationEnum location : LocationEnum.values()) {
            if (location.name().equals("UNKNOWN")) {
                continue;
            }

            result.add(
                    load(type, localDateTime, predictionDay, location));
        }
        return result;
    }

    private Weather connect(OpenApiType type, URI uri, int predictionDay) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            OpenApiParser parser = parserFactory.generate(type);

            for (int rounds = 0; rounds < 5; rounds++) {
                try {
                    ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
                    Map<?,?> data = parser.xmlResponseToMap(responseEntity);
                    CommonApiResponseDto responseDto = parser.mapToResponseDto(data, predictionDay);
                    return new Weather(type, responseDto);
                } catch (IllegalArgumentException e) {
                    log.warn(e.getMessage());
                    log.warn("something wrong... retry after 15 seconds {}", uri);
                    TimeUnit.SECONDS.sleep(15);
                } catch (RestClientException e){
                    log.error("API ERROR {}", e.getMessage());
                    throw e;
                }
            }

        } catch (InterruptedException | JsonProcessingException e) {
            throw new RuntimeException("Load Fail, : {}", e);
        }

        throw new RuntimeException("Load Fail");
    }

}
