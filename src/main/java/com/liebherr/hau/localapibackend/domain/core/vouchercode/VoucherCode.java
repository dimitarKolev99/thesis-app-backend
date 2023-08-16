package com.liebherr.hau.localapibackend.domain.core.vouchercode;

import com.liebherr.hau.localapibackend.domain.core.serialnumber.ApplianceSerialNumber;
import com.liebherr.hau.localapibackend.domain.core.user.User;
import jakarta.persistence.*;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import com.liebherr.hau.localapibackend.domain.core.user.User;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "voucher_code")
public class VoucherCode {

    public static final Logger LOGGER = LoggerFactory.getLogger(VoucherCode.class);

    @Id
    @Column(name = "voucher_code_id")
    private UUID voucherCodeId = UUID.randomUUID();

    @Column
    private String code;

//    @Column
//    @Enumerated(EnumType.STRING)
//    protected AlarmType alarmType;

//    @Column
//    @Enumerated(EnumType.STRING)
//    protected AlarmState alarmState = AlarmState.ON;

    @Column
    private Instant createdAt;

    @Column
    @Enumerated(EnumType.STRING)
    private Company obtainedFromCompany;

    @Column
    @Enumerated(EnumType.STRING)
    private Status voucherCodeStatus;

    @Transient
    private ProductType productType;

    @Column
    private boolean timed;

    @Column
    private Instant expiresAt;

    @ManyToOne(fetch = FetchType.EAGER, optional = false) //optional = false doc: https://stackoverflow.com/questions/3331907/what-is-the-difference-between-manytooneoptional-false-vs-columnnullable-f
    @JoinColumn(name = "voucher_code_user_id")
    private User user;

    // one to one
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "voucherCode", cascade = CascadeType.ALL, orphanRemoval = true) //mappedBy: the name of the property in the referenced entity
    private List<ApplianceSerialNumber> serialNumbers = new ArrayList<>();

}
