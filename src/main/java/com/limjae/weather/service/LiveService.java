package com.limjae.weather.service;

import com.limjae.weather.entity.Live;
import com.limjae.weather.entity._global.Weather;
import com.limjae.weather.entity.enums.EntityEnum;
import com.limjae.weather.openapi.service.LiveApiService;
import com.limjae.weather.openapi.type.OpenApiType;
import com.limjae.weather.repository.LiveRepository;
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
public class LiveService {
    private final LiveRepository liveRepository;
    private final LiveApiService liveApiService;

    public EntityEnum getType() {
        return EntityEnum.LIVE;
    }

    @Transactional
    public List<Long> loadToDB(OpenApiType type) {
        return loadAllToDB(type, LocalDateTime.now());
    }

    @Transactional
    public List<Long> loadAllToDB(OpenApiType type, LocalDateTime localDateTime) {
        List<Long> weatherIds = new ArrayList<>();
        List<Weather> weathers = liveApiService.loadAllLocation(
                type, localDateTime
        );
        log.info("load total {} weathers", weathers.size());
        weathers.forEach(weather -> {
            weatherIds.add(saveDistinct(new Live(weather)));
        });
        return weatherIds;
    }

    @Transactional
    public Long saveDistinct(Live live) {
        Live targetWeather = liveRepository.findFirstByWeather_TypeAndWeather_LocationAndWeather_MeasuredDate(
                        live.getWeather().getType(),
                        live.getWeather().getLocation(),
                        live.getWeather().getMeasuredDate())
                .orElse(live);

        return liveRepository.save(targetWeather).getId();
    }

}
