package com.limjae.weather.openapi.type;

/**
 * 실황, 단기(미지원), 중기, 장기(미지원)을 나타냅니다.
 * @see com.limjae.weather.openapi.uri.OpenApiUriFactory OpenApiUriFactory에서 생성자를 구분하는데 사용됩니다.
 * @see com.limjae.weather.openapi.time.OpenApiTimeFactory OpenApiTimeFactory에서 생성자를 구분하는데 사용됩니다.
 */
public enum OpenApiType {
    LIVE,
    SHORT_TERM,
    MID_TERM,
    LONG_TERM
}
