package com.liebherr.hau.localapibackend.utils.mocks;

import lombok.Data;

public @Data class Request {

    private String method;

    private String urlPathPattern;

    private QueryParameters queryParameters;

}
