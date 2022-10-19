package com.limjae.weather.service;

import com.limjae.weather.entity.Weather;
import com.limjae.weather.entity._global.WeatherInfo;
import com.limjae.weather.entity.enums.LocationEnum;
import com.limjae.weather.openapi.service.OpenApiService;
import com.limjae.weather.openapi.type.OpenApiType;
import com.limjae.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final OpenApiService openApiService;

    @Transactional
    public Weather loadToDB(OpenApiType type) {
        WeatherInfo weatherInfo = openApiService.load(type, LocationEnum.SEOUL, LocalDateTime.now().withHour(6));
        return weatherRepository.save(new Weather(weatherInfo, type));
    }

}
