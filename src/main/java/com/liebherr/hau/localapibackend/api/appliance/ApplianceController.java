package com.liebherr.hau.localapibackend.api.appliance;

import com.liebherr.hau.localapibackend.api.appliance.dto.response.GetApplianceStateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static net.logstash.logback.argument.StructuredArguments.kv;

@RestController
public class ApplianceController {

    public static final Logger LOGGER = LoggerFactory.getLogger(ApplianceController.class);

    @Autowired
    ApplianceService applianceService;

    @GetMapping("/appliance")
    public GetApplianceStateResponse getApplianceState(@RequestParam(value = "serial") String serialNumber,
                                                       @RequestParam(value = "id") String userId) {
        return applianceService.checkApplianceSerialNumber(serialNumber, userId);
    }

}
