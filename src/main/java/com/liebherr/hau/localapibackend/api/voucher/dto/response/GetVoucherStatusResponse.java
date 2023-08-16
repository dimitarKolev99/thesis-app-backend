package com.liebherr.hau.localapibackend.api.voucher.dto.response;

import lombok.Data;

public @Data class GetVoucherStatusResponse {
    private String status;
    private String message;
    private String uuid;
    private VoucherCodeResponse voucherCodeResponse;
}
