package com.liebherr.hau.localapibackend.domain.core.vouchercode;

import com.liebherr.hau.localapibackend.api.voucher.dto.response.GetVoucherStatusResponse;
import com.liebherr.hau.localapibackend.domain.core.user.User;
import com.liebherr.hau.localapibackend.domain.core.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherCodeProcess {

    public static final Logger LOGGER = LoggerFactory.getLogger(VoucherCodeProcess.class);

    @Autowired
    UserService userService;

    @Autowired
    VoucherCodeRepository voucherCodeRepository;

    public UUID saveVoucherCodeWithUserId(GetVoucherStatusResponse getVoucherStatusResponse, String userId) {
        VoucherCode voucherCodeEntity = new VoucherCode(); //mutable, compare with Builder which is immutable
        voucherCodeEntity.setCode(getVoucherStatusResponse.getVoucherCodeResponse().getCode());
        voucherCodeEntity.setCreatedAt(getVoucherStatusResponse.getVoucherCodeResponse().getCreatedAt());
        voucherCodeEntity.setTimed(getVoucherStatusResponse.getVoucherCodeResponse().isTimed());
        voucherCodeEntity.setExpiresAt(getVoucherStatusResponse.getVoucherCodeResponse().getExpiresAt());
        voucherCodeEntity.setProductType(getVoucherStatusResponse.getVoucherCodeResponse().getProductType());
        voucherCodeEntity.setObtainedFromCompany(getVoucherStatusResponse.getVoucherCodeResponse().getObtainedFromCompany());

        User user = userService.getByUserId(UUID.fromString(userId));

        voucherCodeEntity.setUser(user);

        return voucherCodeRepository.save(voucherCodeEntity).getVoucherCodeId();
    }

//    public UUID saveEntityWithSerialNumber(String serialNumber, String userId) {
//        VoucherCodeSerialNumberLicense voucherCodeSerialNumberLicense = new VoucherCodeSerialNumberLicense();
//        voucherCodeSerialNumberLicense.setSerialNumber(serialNumber);
//
//        User user = userRepository.findById(UUID.fromString(userId)).orElseThrow();
//
//        voucherCodeSerialNumberLicense.setUser(user);
//
//        VoucherCodeSerialNumberLicense voucherCodeSerialNumberLicenseHolder = voucherCodeSerialNumberLicenseRepository.save(voucherCodeSerialNumberLicense);
//
//        return voucherCodeSerialNumberLicenseHolder.getVoucherSerialLicenseId();
//    }

//    public UUID saveVoucherCode(String voucherCode, String id) throws Exception {
//
//        VoucherCodeSerialNumberLicense o = voucherCodeSerialNumberLicenseRepository.findById(UUID.fromString(id))
//                .orElseThrow();
//
//        o.setVoucherCode(voucherCode);
//
//        LicenseFileRequest licenseFileRequest = new LicenseFileRequest();
//        licenseFileRequest.setSerialNumber(o.getSerialNumber());
//        licenseFileRequest.setVoucherCode(voucherCode);
//        licenseFileRequest.setEmail(o.getUser());
//
//        String encryptedLicenseString = licenseFileService.getEncryptedLicense(licenseFileRequest);
//
//        o.setLicenseFileUri(encryptedLicenseString);
//
//        VoucherCodeSerialNumberLicense vcsn = voucherCodeSerialNumberLicenseRepository.save(o);
//
//        return vcsn.getVoucherSerialLicenseId();
//    }

}
