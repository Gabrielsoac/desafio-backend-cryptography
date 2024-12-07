package com.gabryellow.cryptography.services;

import com.gabryellow.cryptography.entities.Purchase;
import com.gabryellow.cryptography.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService{

    private PurchaseRepository purchaseRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository){
        this.purchaseRepository = purchaseRepository;
    }

    public  PurchaseServiceImpl(){

    }

    @Override
    public Purchase getPurchase(Long id) {
        return null;
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return List.of();
    }

    @Override
    public Purchase createPurchase(String userDocument, String creditCardToken, Long value) {
        return null;
    }

    @Override
    public Purchase updatePurchase(Purchase purchase, Long value) {
        return null;
    }

    @Override
    public void deletePurchase(Purchase purchase) {

    }
}
