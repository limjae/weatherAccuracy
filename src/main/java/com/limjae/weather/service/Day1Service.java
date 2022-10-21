package com.limjae.weather.service;

import com.limjae.weather.entity.Day1;
import com.limjae.weather.entity._global.Weather;
import com.limjae.weather.entity.enums.EntityEnum;
import com.limjae.weather.openapi.service.ShortApiService;
import com.limjae.weather.openapi.type.OpenApiType;
import com.limjae.weather.repository.Day1Repository;
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
public class Day1Service {
    private final Day1Repository day1Repository;
    private final ShortApiService shortApiService;

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
        List<Weather> weathers = shortApiService.loadAllLocation(
                type, localDateTime
        );
        log.info("load total {} weathers", weathers.size());
        weathers.forEach(weather -> {
            log.info("Weather {}",weather);
            weatherIds.add(saveDistinct(new Day1(weather)));
        });
        return weatherIds;
    }

    @Transactional
    public Long saveDistinct(Day1 day1) {
        Day1 targetWeather = day1Repository.findFirstByWeather_TypeAndWeather_LocationAndWeather_MeasuredDate(
                        day1.getWeather().getType(),
                        day1.getWeather().getLocation(),
                        day1.getWeather().getMeasuredDate())
                .orElse(day1);

        return day1Repository.save(targetWeather).getId();
    }

}
