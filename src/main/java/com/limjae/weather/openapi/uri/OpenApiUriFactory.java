package com.limjae.weather.openapi.uri;

import com.limjae.weather.openapi.type.OpenApiType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class OpenApiUriFactory {
    private final Map<OpenApiType, OpenApiUri> uriMap = new HashMap<>();

    public OpenApiUriFactory(List<OpenApiUri> uriList) {
        if (CollectionUtils.isEmpty(uriList)) {
            throw new IllegalArgumentException("존재하는 OpenApiUri 구현체가 없음");
        }

        for (OpenApiUri openApiUri : uriList) {
            this.uriMap.put(openApiUri.getType(), openApiUri);
        }
    }

    public Set<OpenApiType> getTypes() {
        return uriMap.keySet();
    }

    public OpenApiUri generate(OpenApiType type) {
        return uriMap.get(type);
    }
}
