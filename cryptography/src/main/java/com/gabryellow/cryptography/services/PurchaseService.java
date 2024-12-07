package com.gabryellow.cryptography.services;

import com.gabryellow.cryptography.entities.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseService {

    Purchase getPurchase(Long id);
    List<Purchase> getAllPurchases();
    Purchase createPurchase(String userDocument, String creditCardToken, Long value);
    Purchase updatePurchase(Purchase purchase, Long value);
    void deletePurchase(Purchase purchase);
}
