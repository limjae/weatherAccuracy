package com.limjae.weather.service;

import com.limjae.weather.entity.Day2;
import com.limjae.weather.entity._global.Weather;
import com.limjae.weather.entity.enums.EntityEnum;
import com.limjae.weather.openapi.OpenApi;
import com.limjae.weather.openapi.type.OpenApiType;
import com.limjae.weather.repository.Day2Repository;
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
public class Day2Service {
    private final Day2Repository day2Repository;
    private final OpenApi openApi;

    public EntityEnum getType() {
        return EntityEnum.D1;
    }

    @Transactional
    public List<Long> loadToDB(OpenApiType type) {
        return loadAllToDB(type, LocalDateTime.now());
    }

    @Transactional
    public List<Long> loadAllToDB(OpenApiType type, LocalDateTime localDateTime) {
        List<Long> weatherIds = new ArrayList<>();
        List<Weather> weathers = openApi.loadAllLocation(
                type, localDateTime, 2);
        log.debug("load total {} weathers", weathers.size());
        weathers.forEach(weather -> {
            log.debug("Weather {}", weather);
            weatherIds.add(saveDistinct(new Day2(weather)));
        });
        return weatherIds;
    }

    @Transactional
    public Long saveDistinct(Day2 day2) {
        Day2 targetWeather = day2Repository.findFirstByWeather_TypeAndWeather_LocationAndWeather_MeasuredDate(
                        day2.getWeather().getType(),
                        day2.getWeather().getLocation(),
                        day2.getWeather().getMeasuredDate())
                .orElse(day2);

        return day2Repository.save(targetWeather).getId();
    }

}
