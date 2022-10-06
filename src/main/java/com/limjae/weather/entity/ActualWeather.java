package com.limjae.weather.entity;

import com.limjae.weather.entity._global.WeatherInfo;
import com.limjae.weather.entity.enums.ForecastTypeEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "weatherInfo"})
@Getter
public class ActualWeather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private WeatherInfo weatherInfo;

    @Enumerated(EnumType.STRING)
    ForecastTypeEnum type;

    public ActualWeather(WeatherInfo info, ForecastTypeEnum type){
        this.weatherInfo = info;
        this.type = type;
    }
}
