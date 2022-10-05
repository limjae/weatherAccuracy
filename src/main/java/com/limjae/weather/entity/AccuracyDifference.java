package com.limjae.weather.entity;

import com.limjae.weather.entity._global.WeatherEntity;
import com.limjae.weather.openapi.vo.enums.LocationEnum;
import com.limjae.weather.openapi.vo.enums.RainEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "weatherInfo"})
@Getter
public class AccuracyDifference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private WeatherEntity weatherInfo;
}
