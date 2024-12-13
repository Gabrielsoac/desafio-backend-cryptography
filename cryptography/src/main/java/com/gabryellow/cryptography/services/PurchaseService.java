package com.gabryellow.cryptography.services;

import com.gabryellow.cryptography.entities.Purchase;
import com.gabryellow.cryptography.services.DTOs.ResponsePurchaseDTO;

import java.util.List;
import java.util.Optional;

public interface PurchaseService {

    Purchase getPurchase(Long id);
    List<Purchase> getAllPurchases();
    Purchase createPurchase(String userDocument, String creditCardToken, Long value);
    Purchase updatePurchase(Purchase purchase, String userDocument, String creditCardToken, Long value);
    void deletePurchase(Purchase purchase);
}
