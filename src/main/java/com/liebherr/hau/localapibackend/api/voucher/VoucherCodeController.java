package com.liebherr.hau.localapibackend.api.voucher;

import com.liebherr.hau.localapibackend.api.voucher.dto.response.GetVoucherStatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoucherCodeController {

    public static final Logger LOGGER = LoggerFactory.getLogger(VoucherCodeController.class);

    @Autowired
    VoucherCodeService voucherCodeService;

    @GetMapping("/voucher")
    public GetVoucherStatusResponse getVoucherState(@RequestParam(value = "code") String voucherCode,
                                                    @RequestParam(value = "id") String userId) throws Exception {
        return voucherCodeService.getVoucherCodeState(voucherCode, userId);
    }
}
