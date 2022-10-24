package com.limjae.weather.entity;

import com.limjae.weather.entity._global.Weather;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Day2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Weather weather;

    public Day2(Weather weather){
        this.weather = weather;
    }
}
