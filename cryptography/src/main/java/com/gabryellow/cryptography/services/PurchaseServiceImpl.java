package com.gabryellow.cryptography.services;

import com.gabryellow.cryptography.entities.Purchase;
import com.gabryellow.cryptography.repository.PurchaseRepository;
import com.gabryellow.cryptography.services.exceptions.PurchaseNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseServiceImpl implements PurchaseService{

    private final PurchaseRepository purchaseRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository){
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public Purchase getPurchase(Long id) {
        Optional<Purchase> purchaseOptional = purchaseRepository.findById(id);

        if(purchaseOptional.isPresent()){

            return purchaseOptional.get();
        }

        throw new PurchaseNotFoundException("Purchase Not Found");
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    @Override
    public Purchase createPurchase(String userDocument, String creditCardToken, Long value) {
        return purchaseRepository.save(new Purchase(userDocument, creditCardToken, value));
    }

    @Override
    @Transactional
    public Purchase updatePurchase(Purchase purchase, String userDocument, String creditCardToken, Long value) {

        purchase.setValue(value);

        purchase.setCreditCardToken(creditCardToken);
        purchase.setUserDocument(userDocument);

        return purchase;
    }

    @Override
    public void deletePurchase(Purchase purchase) {
        purchaseRepository.delete(purchase);
    }
}
