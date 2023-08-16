package com.liebherr.hau.localapibackend.api.licensefile.dto.request;

import lombok.Data;

public @Data class LicenseFileRequest {
    private String email;
    private String serialNumber;
    private String voucherCode;
}
