package com.liebherr.hau.localapibackend.api.licensefile.dto;

import lombok.Data;

public @Data class LicenseFile {
    private String email;
    private String serialNumber;
    private String voucherCode;

    public LicenseFile() {

    }
}
