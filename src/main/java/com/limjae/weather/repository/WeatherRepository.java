package com.limjae.weather.repository;

import com.limjae.weather.entity.Weather;
import com.limjae.weather.entity.enums.LocationEnum;
import com.limjae.weather.openapi.type.OpenApiType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Optional<Weather> findFirstByTypeAndWeatherInfo_LocationAndWeatherInfo_MeasuredDate
            (OpenApiType type, LocationEnum weatherInfo_location, LocalDateTime weatherInfo_measuredDate);

}
