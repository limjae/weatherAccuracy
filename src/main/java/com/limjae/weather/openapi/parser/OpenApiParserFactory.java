package com.limjae.weather.openapi.parser;

import com.limjae.weather.openapi.type.OpenApiType;
import com.limjae.weather.openapi.uri.OpenApiUri;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class OpenApiParserFactory {
    private final Map<OpenApiType, OpenApiParser> parserMap = new HashMap<>();

    public OpenApiParserFactory(List<OpenApiParser> parserList) {
        if (CollectionUtils.isEmpty(parserList)) {
            throw new IllegalArgumentException("존재하는 OpenApiUri 구현체가 없음");
        }

        for (OpenApiParser parser : parserList) {
            this.parserMap.put(parser.getType(), parser);
        }
    }

    public Set<OpenApiType> getTypes() {
        return parserMap.keySet();
    }

    public OpenApiParser generate(OpenApiType type) {
        return parserMap.get(type);
    }
}
