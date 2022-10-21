package com.limjae.weather.repository;

import com.limjae.weather.entity.Day1;
import com.limjae.weather.entity.Live;
import com.limjae.weather.entity.PredictionEntity;
import com.limjae.weather.entity.enums.LocationEnum;
import com.limjae.weather.openapi.type.OpenApiType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface Day1Repository extends JpaRepository<Day1, Long> {
    Optional<Day1> findFirstByWeather_TypeAndWeather_LocationAndWeather_MeasuredDate
            (OpenApiType type, LocationEnum location, LocalDateTime measuredDate);
}
