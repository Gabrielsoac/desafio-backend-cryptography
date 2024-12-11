package com.gabryellow.cryptography.services;

import com.gabryellow.cryptography.entities.Purchase;
import com.gabryellow.cryptography.repository.PurchaseRepository;
import com.gabryellow.cryptography.services.DTOs.ResponsePurchaseDTO;
import com.gabryellow.cryptography.services.exceptions.PurchaseNotFoundException;
import com.gabryellow.cryptography.util.AESCryptography;
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
    public ResponsePurchaseDTO getPurchase(Long id) {
        Optional<Purchase> purchaseOptional = purchaseRepository.findById(id);

        if(purchaseOptional.isPresent()){

            Purchase purchase = purchaseOptional.get();
            return descryptograph(purchase);
        }

        throw new PurchaseNotFoundException("Purchase not found");
    }

    @Override
    public List<ResponsePurchaseDTO> getAllPurchases() {
        List<Purchase> purchases = purchaseRepository.findAll();
        List<ResponsePurchaseDTO> purchasesDTO = new ArrayList<>();

        for(Purchase purchase : purchases){
            purchasesDTO.add(descryptograph(purchase));
        }

        return purchasesDTO;
    }

    @Override
    public ResponsePurchaseDTO createPurchase(String userDocument, String creditCardToken, Long value) {

        List<String> encryptedData = cryptograph(userDocument, creditCardToken);

        String userDocumentEncrypted = encryptedData.get(0);
        String creditCardTokenEncrypted = encryptedData.get(1);

        Purchase purchase = purchaseRepository.save(new Purchase(userDocumentEncrypted, creditCardTokenEncrypted, value));

        return new ResponsePurchaseDTO(purchase.getId(), userDocument, creditCardToken, value);
    }

    @Override
    @Transactional
    public ResponsePurchaseDTO updatePurchase(Long id, String userDocument, String creditCardToken, Long value) {
        Optional<Purchase> purchaseOptional = purchaseRepository.findById(id);

        if (purchaseOptional.isPresent()){

            Purchase purchase = purchaseOptional.get();
            purchase.setValue(value);

            List<String> encryptedData = cryptograph(userDocument, creditCardToken);

            String userDocumentEncrypted = encryptedData.get(0);
            String creditCardTokenEncrypted = encryptedData.get(1);

            purchase.setCreditCardToken(creditCardTokenEncrypted);
            purchase.setUserDocument(userDocumentEncrypted);

            return descryptograph(purchase);
        }

        throw new PurchaseNotFoundException("Purchase Not Found");
    }

    @Override
    public void deletePurchase(Long id) {
        Optional<Purchase> purchaseOptional = purchaseRepository.findById(id);

        if (!purchaseOptional.isPresent()) throw new PurchaseNotFoundException("Purchase not found");

        purchaseRepository.delete(purchaseOptional.get());
    }

    private ResponsePurchaseDTO descryptograph(Purchase purchase){
        String creditCardTokenDecrypted = AESCryptography.decrypt(purchase.getCreditCardToken());
        String userDocumentDecrypted = AESCryptography.decrypt(purchase.getUserDocument());
        return new ResponsePurchaseDTO(purchase.getId(), userDocumentDecrypted,  creditCardTokenDecrypted, purchase.getValue());
    }

    private List<String> cryptograph(String userDocument, String creditCardToken){

        String userDocumentEncrypted = AESCryptography.encrypt(userDocument);
        String creditCardTokenEncrypted = AESCryptography.encrypt(creditCardToken);

        return List.of(userDocumentEncrypted, creditCardTokenEncrypted);
    }
}
