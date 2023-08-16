package com.liebherr.hau.localapibackend.api.voucher.dto.response;

import com.liebherr.hau.localapibackend.domain.core.vouchercode.Company;
import com.liebherr.hau.localapibackend.domain.core.vouchercode.ProductType;
import com.liebherr.hau.localapibackend.domain.core.vouchercode.Status;
import lombok.Data;

import java.time.Instant;

public @Data class VoucherCodeResponse {

    private String code;

    private Instant createdAt;

    private Company obtainedFromCompany;

    private Status voucherCodeStatus;

    private ProductType productType;

    private boolean timed;

    private Instant expiresAt;

}
