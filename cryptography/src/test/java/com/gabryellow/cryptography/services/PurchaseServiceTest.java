package com.gabryellow.cryptography.services;

import com.gabryellow.cryptography.builders.PurchaseBuilder;
import com.gabryellow.cryptography.entities.Purchase;
import com.gabryellow.cryptography.repository.PurchaseRepository;
import com.gabryellow.cryptography.services.DTOs.ResponsePurchaseDTO;
import com.gabryellow.cryptography.services.exceptions.PurchaseNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.Optional;

@SpringBootTest
public class PurchaseServiceTest {

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("key.value", () -> "hXHsbw8VC7ukQn5i8MUgZKSfVOff2c7=");
    }

    PurchaseRepository purchaseRepository = Mockito.mock(PurchaseRepository.class);
    PurchaseService purchaseService;

    @BeforeEach
    void setUp(){
        purchaseService = new PurchaseServiceImpl(purchaseRepository);
    }

    @Test
    @DisplayName("Should get Purchase with sucess")
    void getPurchaseWithSucess(){
        Mockito.when(purchaseRepository.findById(Mockito.anyLong()))
            .thenReturn(Optional.of(PurchaseBuilder.aPurchase().build()));

        ResponsePurchaseDTO purchase = purchaseService.getPurchase(1L);

        Assertions.assertAll("Purchase Data",
                () -> Assertions.assertEquals(1L, purchase.id()),
                () -> Assertions.assertEquals("12340987", purchase.userDocument()),
                () -> Assertions.assertEquals("1234-5432-2123-5423", purchase.creditCardToken()),
                () -> Assertions.assertEquals(20L, purchase.value()));
    }

    @Test
    @DisplayName("Should to refuse create a Purchase")
    void refuseCreatePurchase(){
        Mockito.when(purchaseRepository.findById(Mockito.anyLong()))
            .thenReturn(Optional.empty());

        String message = Assertions.assertThrows(PurchaseNotFoundException.class,
                () -> purchaseService.getPurchase(1L)).getMessage();

        Assertions.assertEquals("Purchase Not Found", message);
    }

    @Test
    @DisplayName("Should to create Purchase with sucess")
    void createPurchaseWithSucess(){

        Mockito.when(purchaseRepository.save(Mockito.any(Purchase.class)))
            .thenReturn(PurchaseBuilder.aPurchase().build());

        purchaseService.createPurchase(
            "12340987",
            "1234-5432-2123-5423",
            20L );

        Mockito.verify(purchaseRepository,
                Mockito.times(1))
                .save(Mockito.any(Purchase.class));
    }

    @Test
    @DisplayName("Should to update Purchase with sucess")
    void updatePurchaseWithSucess(){

        Mockito.when(purchaseRepository.findById(Mockito.anyLong()))
            .thenReturn(Optional.of(PurchaseBuilder.aPurchase().build()));

        ResponsePurchaseDTO purchase = purchaseService.updatePurchase(
                1L, "1234322", "1234543265437654", 30L);

        Assertions.assertEquals(30L, purchase.value());
    }

    @Test
    @DisplayName("Should to refuse update a Purchase")
    void refuseUpdatePurchase(){
        Mockito.when(purchaseRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        String message = Assertions.assertThrows(PurchaseNotFoundException.class,
                () -> purchaseService.updatePurchase(
                        1L, "1234322", "1234543265437654", 30L ))
                        .getMessage();

        Assertions.assertEquals("Purchase Not Found", message);
    }

}
