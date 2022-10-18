package com.limjae.weather.openapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.limjae.weather.entity.ActualWeather;
import com.limjae.weather.entity.enums.ForecastTypeEnum;
import com.limjae.weather.entity.enums.LocationEnum;
import com.limjae.weather.openapi.basetime.OpenApiBaseTime;
import com.limjae.weather.openapi.basetime.OpenApiBaseTimeFactory;
import com.limjae.weather.openapi.type.OpenApiType;
import com.limjae.weather.openapi.uri.OpenApiUriFactory;
import com.limjae.weather.openapi.uri.impl.LongForeCastURI;
import com.limjae.weather.entity._global.WeatherInfo;
import com.limjae.weather.openapi.dto.OpenAPIResponseDto;
import com.limjae.weather.openapi.uri.impl.LiveForecastURI;
import com.limjae.weather.repository.ActualWeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenAPIService {
    private final LiveForecastURI liveForeCastURI;
    private final LongForeCastURI longForeCastURI;

    private final ActualWeatherRepository actualWeatherRepository;

    public WeatherInfo loadShortForeCast(LocationEnum location) throws JsonProcessingException, InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        OpenApiBaseTime baseTime = OpenApiBaseTimeFactory.getOpenApiBaseTime(OpenApiType.LIVE, LocalDateTime.now().withHour(6));

        URI uri = liveForeCastURI.getURI(baseTime, location);

        for (int rounds = 0; rounds < 5; rounds++) {
            ResponseEntity<String> forEntity = restTemplate.getForEntity(uri, String.class);

            XmlMapper xmlMapper = new XmlMapper();
            Map data = xmlMapper.readValue(forEntity.getBody(), Map.class);

            if (data.containsKey("cmmMsgHeader")) {
                log.warn("errMsg = {}", data);
                log.warn("retry after 15 seconds {}", location.name());
                TimeUnit.SECONDS.sleep(15);
            } else {
                ObjectMapper mapper = new ObjectMapper();
                OpenAPIResponseDto responseDTO = mapper.convertValue(data, OpenAPIResponseDto.class);
                responseDTO.getBody().getItems().getItem().forEach(i -> log.info("info = " + i));

                return new WeatherInfo(responseDTO.getBody().getItems());
            }
        }

        throw new RuntimeException("Load Fail");
    }

    public List<WeatherInfo> loadAllForecasts() throws JsonProcessingException, InterruptedException {
        List<WeatherInfo> result = new ArrayList<>();
        OpenApiBaseTime baseTime = OpenApiBaseTimeFactory.getOpenApiBaseTime(OpenApiType.LIVE, LocalDateTime.now().withHour(6));
        for (LocationEnum location : LocationEnum.values()) {
            if (location.name().equals("UNKNOWN")) {
                continue;
            }

            result.add(
                    loadShortForeCast(location));
        }
        return result;
    }

    @Transactional
    public ActualWeather loadForeCastToDB() throws JsonProcessingException, InterruptedException {
        OpenApiBaseTime liveTime = OpenApiBaseTimeFactory.getOpenApiBaseTime(OpenApiType.LIVE, LocalDateTime.now().withHour(6));

        WeatherInfo weatherInfo = loadShortForeCast(LocationEnum.SEOUL);
        return actualWeatherRepository.save(new ActualWeather(weatherInfo, ForecastTypeEnum.SHORT));
    }

}
