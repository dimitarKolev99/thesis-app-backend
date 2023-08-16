package com.liebherr.hau.localapibackend.api.appliance;

import com.liebherr.hau.localapibackend.domain.core.vouchercode.VoucherCodeProcess;
import com.liebherr.hau.localapibackend.api.appliance.dto.response.GetApplianceStateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplianceService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ApplianceService.class);

    @Autowired
    private ApplianceGateway applianceGateway;

    @Autowired
    VoucherCodeProcess voucherCodeProcess;

    public GetApplianceStateResponse checkApplianceSerialNumber(String serialNumber, String userId) {

        GetApplianceStateResponse getApplianceStateResponse = applianceGateway.getApplianceStatusBySerialNumber(serialNumber);

        if (getApplianceStateResponse.getStatus().equals("Success") && getApplianceStateResponse.getMessage().equals("Appliance exists")) {

//            UUID uuid = voucherCodeProcess.saveEntityWithSerialNumber(serialNumber, userId);
            getApplianceStateResponse.setUuid(UUID.randomUUID().toString());
        }

        return getApplianceStateResponse;
    }

}
