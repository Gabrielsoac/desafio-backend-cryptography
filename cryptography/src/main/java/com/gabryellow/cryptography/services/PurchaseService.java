package com.gabryellow.cryptography.services;

import com.gabryellow.cryptography.entities.Purchase;
import com.gabryellow.cryptography.services.DTOs.ResponsePurchaseDTO;

import java.util.List;
import java.util.Optional;

public interface PurchaseService {

    ResponsePurchaseDTO getPurchase(Long id);
    List<Purchase> getAllPurchases();
    ResponsePurchaseDTO createPurchase(String userDocument, String creditCardToken, Long value);
    ResponsePurchaseDTO updatePurchase(Purchase purchase, Long value);
    void deletePurchase(Purchase purchase);
}
