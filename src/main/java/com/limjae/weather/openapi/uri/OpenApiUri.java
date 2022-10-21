package com.limjae.weather.openapi.uri;

import com.limjae.weather.openapi.type.OpenApiType;

import java.net.URI;

public interface OpenApiUri {

    public OpenApiType getType();

    public URI getURI();

    public URI getURI(OpenApiParameter openApiParameter);
}
