package com.gabryellow.cryptography.controllers;

import com.gabryellow.cryptography.services.DTOs.RequestPurchaseDTO;
import com.gabryellow.cryptography.services.DTOs.ResponsePurchaseDTO;
import com.gabryellow.cryptography.services.PurchaseService;
import com.gabryellow.cryptography.services.PurchaseServiceImpl;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
        return ResponseEntity.ok(purchaseService.getAllPurchases());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePurchaseDTO> getPurchase(@PathVariable Long id){
        ResponsePurchaseDTO responsePurchaseDTO = purchaseService.getPurchase(id);

        return ResponseEntity.ok(responsePurchaseDTO);
    }

    @PostMapping
    public ResponseEntity<ResponsePurchaseDTO> createPurchase(@RequestBody RequestPurchaseDTO data){
        ResponsePurchaseDTO responsePurchaseDTO = purchaseService.createPurchase(data.userDocument(), data.creditCardToken(), data.value());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(responsePurchaseDTO.id()).toUri();
        return ResponseEntity.created(location).body(responsePurchaseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePurchaseDTO> updatePurchase(@PathVariable Long id, @RequestBody RequestPurchaseDTO data){
        ResponsePurchaseDTO responsePurchaseDTO = purchaseService.updatePurchase(
                id,
                data.userDocument(),
                data.creditCardToken(),
                data.value());

        return ResponseEntity.ok(responsePurchaseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Long id){
        purchaseService.deletePurchase(id);
        return ResponseEntity.noContent().build();
    }
}
