package com.liebherr.hau.localapibackend.api.voucher;

import com.liebherr.hau.localapibackend.api.voucher.dto.response.GetVoucherStatusResponse;
import com.liebherr.hau.localapibackend.domain.core.vouchercode.VoucherCodeProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherCodeService {

    @Autowired
    VoucherCodeGateway voucherCodeGateway;

    @Autowired
    VoucherCodeProcess voucherCodeProcess;

    public GetVoucherStatusResponse getVoucherCodeState(String voucherCode, String userId) throws Exception {

        GetVoucherStatusResponse getVoucherStatusResponse = voucherCodeGateway.checkVoucherStatus(voucherCode);

        if (getVoucherStatusResponse.getStatus().equals("exists") && getVoucherStatusResponse.getMessage().equals("Voucher Code exists")) {
            UUID uuid = voucherCodeProcess.saveVoucherCodeWithUserId(getVoucherStatusResponse, userId);
            getVoucherStatusResponse.setUuid(uuid.toString());
        }

        return getVoucherStatusResponse;
    }

}
