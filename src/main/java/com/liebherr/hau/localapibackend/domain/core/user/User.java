package com.liebherr.hau.localapibackend.domain.core.user;

import com.liebherr.hau.localapibackend.domain.core.vouchercode.VoucherCode;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    private UUID userId = UUID.randomUUID();

    @Column(name = "email")
    private String email;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<VoucherCodeSerialNumberLicense> voucherCodeSerialNumberLicenses = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoucherCode> voucherCodes = new ArrayList<>();

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<License> licenses = new ArrayList<>();

    public User() {}

}
