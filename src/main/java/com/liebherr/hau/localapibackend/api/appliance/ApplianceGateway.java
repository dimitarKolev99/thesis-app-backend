package com.liebherr.hau.localapibackend.api.appliance;

import com.liebherr.hau.localapibackend.api.appliance.dto.response.GetApplianceStateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ApplianceGateway {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplianceGateway.class);

    @Autowired
    private WebClient applianceClient;

    public GetApplianceStateResponse getApplianceStatusBySerialNumber(String serialNumber) {

        GetApplianceStateResponse getApplianceStateResponse = new GetApplianceStateResponse();

        switch (serialNumber) {
            case "d8b25784-c16f-449a-9006-6972e8a9111b" -> {
                getApplianceStateResponse.setStatus("Success");
                getApplianceStateResponse.setMessage("Appliance exists");
            }
            case "d7b25784-c16f-449a-9006-6972e8a9111b" -> {
                getApplianceStateResponse.setStatus("Already Licensed");
                getApplianceStateResponse.setMessage("Appliance is already licensed");
            }
            default -> {
                getApplianceStateResponse.setStatus("Error");
                getApplianceStateResponse.setMessage("Appliance does not exist");
            }
        }

        return getApplianceStateResponse;
    }

}
