package com.liebherr.hau.localapibackend.domain.core.vouchercode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoucherCodeRepository extends JpaRepository<VoucherCode, UUID> {
}
