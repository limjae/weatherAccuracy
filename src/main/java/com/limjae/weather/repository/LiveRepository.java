package com.limjae.weather.repository;

import com.limjae.weather.entity.Live;
import com.limjae.weather.entity.PredictionEntity;
import com.limjae.weather.entity._global.Weather;
import com.limjae.weather.entity.enums.LocationEnum;
import com.limjae.weather.openapi.type.OpenApiType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface LiveRepository extends JpaRepository<Live, Long> {
    Optional<Live> findFirstByWeather_TypeAndWeather_LocationAndWeather_MeasuredDate
            (OpenApiType type, LocationEnum location, LocalDateTime measuredDate);
}
