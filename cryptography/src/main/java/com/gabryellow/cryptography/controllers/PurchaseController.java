package com.gabryellow.cryptography.controllers;

import com.gabryellow.cryptography.services.DTOs.RequestPurchaseDTO;
import com.gabryellow.cryptography.services.DTOs.ResponsePurchaseDTO;
import com.gabryellow.cryptography.services.PurchaseService;
import com.gabryellow.cryptography.services.PurchaseServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/purchase")
@CrossOrigin("*")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseServiceImpl purchaseService){
        this.purchaseService = purchaseService;
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
}
