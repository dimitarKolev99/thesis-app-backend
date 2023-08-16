package com.liebherr.hau.localapibackend.domain.core.serialnumber;

import com.liebherr.hau.localapibackend.domain.core.vouchercode.VoucherCode;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "appliance_serial_number")
public class ApplianceSerialNumber {

    @Id
    @Column(name = "appliance_serial_number_id")
    private UUID applianceSerialNumberId = UUID.randomUUID();

    @Column
    private String serialNumber;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "appliance_serial_number_voucher_code_id")
    private VoucherCode voucherCode;

}
