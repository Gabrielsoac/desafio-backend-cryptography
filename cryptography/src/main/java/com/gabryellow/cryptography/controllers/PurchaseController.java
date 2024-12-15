package com.gabryellow.cryptography.controllers;

import com.gabryellow.cryptography.entities.Purchase;
import com.gabryellow.cryptography.services.DTOs.RequestPurchaseDTO;
import com.gabryellow.cryptography.services.DTOs.ResponsePurchaseDTO;
import com.gabryellow.cryptography.services.PurchaseService;
import com.gabryellow.cryptography.services.PurchaseServiceImpl;
import com.gabryellow.cryptography.util.AESCryptography;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/purchase")
@CrossOrigin("*")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseServiceImpl purchaseService){
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public ResponseEntity<List<ResponsePurchaseDTO>> getAllPurchases(){

        List<Purchase> purchases = purchaseService.getAllPurchases();
        List<ResponsePurchaseDTO> purchasesDTO = new ArrayList<>();

        for(Purchase purchase : purchases){
            purchasesDTO.add(descryptograph(purchase));
        }

        return ResponseEntity.ok(purchasesDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePurchaseDTO> getPurchase(@PathVariable Long id){
        Purchase purchase = purchaseService.getPurchase(id);
        return ResponseEntity.ok(descryptograph(purchase));
    }

    @PostMapping
    public ResponseEntity<ResponsePurchaseDTO> createPurchase(@RequestBody RequestPurchaseDTO data){

        validateData(data.userDocument());
        validateData(data.creditCardToken());
        validateValue(data.value());

        List<String> encryptedData = cryptograph(data.userDocument(), data.creditCardToken());

        String userDocumentEncrypted = encryptedData.get(0);
        String creditCardTokenEncrypted = encryptedData.get(1);

        Purchase purchase = purchaseService.createPurchase(userDocumentEncrypted, creditCardTokenEncrypted, data.value());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(purchase.getId()).toUri();

        return ResponseEntity.created(location).body(descryptograph(purchase));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePurchaseDTO> updatePurchase(@PathVariable Long id, @RequestBody RequestPurchaseDTO data){

        validateData(data.userDocument());
        validateData(data.creditCardToken());
        validateValue(data.value());

        Purchase purchase = purchaseService.getPurchase(id);

        List<String> encryptedData = cryptograph(data.userDocument(), data.creditCardToken());

        String userDocumentEncrypted = encryptedData.get(0);
        String creditCardTokenEncrypted = encryptedData.get(1);

        Purchase purchaseUpdated = purchaseService.updatePurchase(purchase, userDocumentEncrypted, creditCardTokenEncrypted, data.value());

        return ResponseEntity.ok(descryptograph(purchaseUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Long id){
        Purchase purchase = purchaseService.getPurchase(id);
        purchaseService.deletePurchase(purchase);
        return ResponseEntity.noContent().build();
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

    private void validateData(String data){
        if(data == null || data.isEmpty()) throw new IllegalArgumentException("Data cannot be null or empty");
    }
    private void validateValue(Long value){
        if(value == null || value <= 0) throw new IllegalArgumentException("Value cannot be null or less than zero");
    }
}
