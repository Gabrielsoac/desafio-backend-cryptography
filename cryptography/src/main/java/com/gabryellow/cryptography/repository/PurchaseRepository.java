package com.gabryellow.cryptography.repository;

import com.gabryellow.cryptography.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
