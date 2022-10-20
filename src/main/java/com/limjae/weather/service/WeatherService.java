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
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final OpenApiService openApiService;

    @Transactional
    public Weather loadToDB(OpenApiType type) {
        List<WeatherInfo> weathers = openApiService.loadAllLocation(type, LocalDateTime.now().withHour(6));
        log.info("load total {} weathers", weathers.size());
        weathers.forEach(weatherInfo -> {
            saveDistinct(new Weather(weatherInfo, type));
        });

        WeatherInfo weatherInfo = openApiService.load(type, LocalDateTime.now().withHour(6), LocationEnum.SEOUL);
        return saveDistinct(new Weather(weatherInfo, type));
    }

    @Transactional
    public Weather saveDistinct(Weather weather) {
        Weather targetWeather = weatherRepository.findFirstByTypeAndWeatherInfo_LocationAndWeatherInfo_MeasuredDate(
                        weather.getType(),
                        weather.getWeatherInfo().getLocation(),
                        weather.getWeatherInfo().getMeasuredDate())
                .orElse(weather);

        return weatherRepository.save(targetWeather);
    }

}
