package com.liebherr.hau.localapibackend.api.appliance.dto.response;

import lombok.Data;

public @Data class GetApplianceStateResponse {
    private String status;
    private String message;
    private String uuid;
}
