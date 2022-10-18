package com.limjae.weather.openapi.uri;

import com.limjae.weather.entity.enums.LocationEnum;
import com.limjae.weather.openapi.basetime.OpenApiBaseTime;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

public interface OpenApiUri {

    public URI getURI();

    public URI getURI(OpenApiBaseTime baseTime, LocationEnum location);

}
