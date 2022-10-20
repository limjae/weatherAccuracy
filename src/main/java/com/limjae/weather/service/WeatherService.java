package com.limjae.weather.service;

import com.limjae.weather.entity.Weather;
import com.limjae.weather.entity._global.WeatherInfo;
import com.limjae.weather.entity.enums.LocationEnum;
import com.limjae.weather.openapi.service.OpenApiService;
import com.limjae.weather.openapi.type.OpenApiType;
import com.limjae.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final OpenApiService openApiService;

    @Transactional
    public List<Long> loadToDB(OpenApiType type) {
        return loadToDB(type, 6);
    }

    @Transactional
    public List<Long> loadToDB(OpenApiType type, int hour) {
        List<Long> weatherIds = new ArrayList<>();
        List<WeatherInfo> weathers = openApiService.loadAllLocation(
                type,
                LocalDateTime.now().withHour(hour)
        );
        log.info("load total {} weathers", weathers.size());
        weathers.forEach(weatherInfo -> {
            weatherIds.add(saveDistinct(new Weather(weatherInfo, type)));
        });
        return weatherIds;
    }

    @Transactional
    public Long saveDistinct(Weather weather) {
        Weather targetWeather = weatherRepository.findFirstByTypeAndWeatherInfo_LocationAndWeatherInfo_MeasuredDate(
                        weather.getType(),
                        weather.getWeatherInfo().getLocation(),
                        weather.getWeatherInfo().getMeasuredDate())
                .orElse(weather);

        return weatherRepository.save(targetWeather).getId();
    }

}
