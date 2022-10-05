package com.limjae.weather.entity._global;

import com.limjae.weather.openapi.vo.enums.LocationEnum;
import com.limjae.weather.openapi.vo.enums.RainEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeatherEntity {
    @Column(nullable = false)
    private LocationEnum location;

    // celsius
    @Column(nullable = false)
    private double temperature;

    // mm/h
    @Column(nullable = false)
    private double rainPerHour;

    // m/s
    @Column(nullable = false)
    private double windSpeedHorizontal;

    // m/s
    @Column(nullable = false)
    private double windSpeedVertical;

    // %
    @Column(nullable = false)
    private double humidity;

    // degree
    @Column(nullable = false)
    private double windDirection;

    // m/s
    @Column(nullable = false)
    private double windSpeed;

    // PTY
    @Column(nullable = false)
    private RainEnum rainCondition;
}
