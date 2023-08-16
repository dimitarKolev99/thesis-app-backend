package com.liebherr.hau.localapibackend.api.voucher;

import com.liebherr.hau.localapibackend.api.voucher.dto.response.GetVoucherStatusResponse;
import com.liebherr.hau.localapibackend.api.voucher.dto.response.VoucherCodeResponse;
import com.liebherr.hau.localapibackend.domain.core.vouchercode.Company;
import com.liebherr.hau.localapibackend.domain.core.vouchercode.ProductType;
import com.liebherr.hau.localapibackend.domain.core.vouchercode.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class VoucherCodeGateway {

    @Autowired
    private WebClient voucherClient;

    public GetVoucherStatusResponse checkVoucherStatus(String voucherCode) {

        GetVoucherStatusResponse getVoucherStatusResponse = new GetVoucherStatusResponse();

        Instant instantCreated = LocalDateTime.of(2020, 1, 1, 1, 1, 1).toInstant(
                ZoneOffset.UTC);

        Instant instantExpires = LocalDateTime.of(2022, 1, 1, 1, 1, 1).toInstant(
                ZoneOffset.UTC);

        com.liebherr.hau.localapibackend.domain.core.vouchercode.ProductType productType = ProductType.LOCALAPI;
        com.liebherr.hau.localapibackend.domain.core.vouchercode.Company company = Company.LIEBHERR;
        com.liebherr.hau.localapibackend.domain.core.vouchercode.Status status = Status.AVAILABLE;

        VoucherCodeResponse voucherCodeResponse = new VoucherCodeResponse();
        voucherCodeResponse.setCode(voucherCode);
        voucherCodeResponse.setCreatedAt(instantCreated);
        voucherCodeResponse.setExpiresAt(instantExpires);
        voucherCodeResponse.setTimed(true);
        voucherCodeResponse.setProductType(productType);
        voucherCodeResponse.setObtainedFromCompany(company);
        voucherCodeResponse.setVoucherCodeStatus(status);

        switch (voucherCode) {
            case "XCGK-12VM" -> {
                getVoucherStatusResponse.setStatus("exists");
                getVoucherStatusResponse.setMessage("Voucher Code exists");
                getVoucherStatusResponse.setVoucherCodeResponse(voucherCodeResponse);
            }
            case "XCGK-11VM" -> {
                getVoucherStatusResponse.setStatus("Already Redeemed");
                getVoucherStatusResponse.setMessage("Voucher Code is already Redeemed");
                getVoucherStatusResponse.setVoucherCodeResponse(null);
            }
            default -> {
                getVoucherStatusResponse.setStatus("not exists");
                getVoucherStatusResponse.setMessage("Voucher Code does not exist");
                getVoucherStatusResponse.setVoucherCodeResponse(null);
            }
        }

        return getVoucherStatusResponse;
    }

}
