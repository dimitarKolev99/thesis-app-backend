package com.liebherr.hau.localapibackend.utils.mocks;

import lombok.Data;

public @Data class MockRequestResponse {

    private String priority;

    private Request request;

    private Response response;

}
