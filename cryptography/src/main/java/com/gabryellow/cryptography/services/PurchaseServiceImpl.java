package com.gabryellow.cryptography.services;

import com.gabryellow.cryptography.entities.Purchase;
import com.gabryellow.cryptography.repository.PurchaseRepository;
import com.gabryellow.cryptography.services.DTOs.ResponsePurchaseDTO;
import com.gabryellow.cryptography.services.exceptions.PurchaseNotFoundException;
import com.gabryellow.cryptography.util.AESCryptography;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseServiceImpl implements PurchaseService{

    @Value("${key.value}")
    private String key;

    private final PurchaseRepository purchaseRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository){
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public ResponsePurchaseDTO getPurchase(Long id) {
        Optional<Purchase> purchaseOptional = purchaseRepository.findById(id);

        if(purchaseOptional.isPresent()){

            Purchase purchase = purchaseOptional.get();

            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            String creditCardTokenDecrypted = AESCryptography.decrypt(purchase.getCreditCardToken(), secretKey);
            String userDocumentDecrypted = AESCryptography.decrypt(purchase.getUserDocument(), secretKey);
            return new ResponsePurchaseDTO(purchase.getId(), creditCardTokenDecrypted, userDocumentDecrypted, purchase.getValue());
        }

        throw new PurchaseNotFoundException("Purchase not found");
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return List.of();
    }

    @Override
    public ResponsePurchaseDTO createPurchase(String userDocument, String creditCardToken, Long value) {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        String userDocumentEncrypted = AESCryptography.encrypt(userDocument, secretKey);
        String creditCardTokenEncrypted = AESCryptography.encrypt(creditCardToken, secretKey);

        Purchase purchase = purchaseRepository.save(new Purchase(userDocumentEncrypted, creditCardTokenEncrypted, value));

        return new ResponsePurchaseDTO(purchase.getId(), userDocument, creditCardToken, value);
    }

    @Override
    public ResponsePurchaseDTO updatePurchase(Purchase purchase, Long value) {
        return null;
    }

    @Override
    public void deletePurchase(Purchase purchase) {

    }
}
