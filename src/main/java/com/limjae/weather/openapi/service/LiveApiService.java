package com.limjae.weather.openapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.limjae.weather.entity._global.Weather;
import com.limjae.weather.entity.enums.LocationEnum;
import com.limjae.weather.openapi.dto.CommonApiResponseDto;
import com.limjae.weather.openapi.dto.LiveAPIResponseDto;
import com.limjae.weather.openapi.time.OpenApiTime;
import com.limjae.weather.openapi.time.OpenApiTimeFactory;
import com.limjae.weather.openapi.type.OpenApiType;
import com.limjae.weather.openapi.uri.OpenApiParameter;
import com.limjae.weather.openapi.uri.OpenApiUri;
import com.limjae.weather.openapi.uri.OpenApiUriFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LiveApiService {
    private final OpenApiUriFactory uriFactory;
    private final OpenApiTimeFactory baseTimeFactory;

    public Weather load(OpenApiType type, LocalDateTime localDateTime, LocationEnum location) {
        OpenApiTime apiTime = baseTimeFactory.generate(type);
        OpenApiUri openApiUri = uriFactory.generate(type);
        OpenApiParameter openApiParameter = new OpenApiParameter(apiTime, localDateTime, location);
        URI uri = openApiUri.getURI(openApiParameter);

        return connect(type, uri);
    }

    public List<Weather> loadAllLocation(OpenApiType type, LocalDateTime localDateTime) {
        List<Weather> result = new ArrayList<>();

        for (LocationEnum location : LocationEnum.values()) {
            if (location.name().equals("UNKNOWN")) {
                continue;
            }

            result.add(
                    load(type, localDateTime, location));
        }
        return result;
    }

    /**
     * 확장 병목 지점...
     *
     * @param uri
     * @return
     */
    private Weather connect(OpenApiType type, URI uri) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            for (int rounds = 0; rounds < 5; rounds++) {
                ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
                XmlMapper xmlMapper = new XmlMapper();
                Map data = xmlMapper.readValue(responseEntity.getBody(), Map.class);
                log.debug("Api responseEntity: {}", responseEntity);
                log.debug("Api data: {}", data);


                ObjectMapper mapper = new ObjectMapper();
                try {
                    LiveAPIResponseDto liveAPIResponseDto = mapper.convertValue(data, LiveAPIResponseDto.class);
                    if (liveAPIResponseDto.getHeader().getResultCode() != 01){
                        throw new RuntimeException("Api Parameter Error");
                    }
                    if (liveAPIResponseDto.getHeader().getResultCode() != 00) {
                        throw new IllegalArgumentException("API 오류 입니다: " + data.toString());
                    }

                    CommonApiResponseDto responseDto = new CommonApiResponseDto(liveAPIResponseDto);
                    return new Weather(type, responseDto);
                } catch (IllegalArgumentException e) {
                    log.warn("errMsg = {}", data);
                    log.warn("retry after 15 seconds {}", uri);
                    TimeUnit.SECONDS.sleep(15);
                }
            }

        } catch (InterruptedException | JsonProcessingException e) {
            throw new RuntimeException("Load Fail: {}", e);
        }

        throw new RuntimeException("Load Fail");
    }

}
