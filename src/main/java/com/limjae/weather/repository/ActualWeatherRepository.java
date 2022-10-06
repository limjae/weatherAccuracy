package com.limjae.weather.repository;

import com.limjae.weather.entity.ActualWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActualWeatherRepository extends JpaRepository<ActualWeather, Long> {

}
