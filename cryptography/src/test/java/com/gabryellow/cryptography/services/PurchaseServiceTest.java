package com.gabryellow.cryptography.services;

import com.gabryellow.cryptography.builders.PurchaseBuilder;
import com.gabryellow.cryptography.entities.Purchase;
import com.gabryellow.cryptography.repository.PurchaseRepository;
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

        Purchase purchase = purchaseService.getPurchase(1L);

        Assertions.assertAll("Purchase Data",
                () -> Assertions.assertEquals(1L, purchase.getId()),
                () -> Assertions.assertEquals("OzgDHDLIKJk2FDQy5xclhA==", purchase.getUserDocument()),
                () -> Assertions.assertEquals("qy//miOz0SJ8gs0L3cyNuVe204t47h4TICqP5J+4iFE=", purchase.getCreditCardToken()),
                () -> Assertions.assertEquals(20L, purchase.getValue()));
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
            "OzgDHDLIKJk2FDQy5xclhA==",
            "qy//miOz0SJ8gs0L3cyNuVe204t47h4TICqP5J+4iFE=",
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

        Purchase purchase = purchaseService.updatePurchase(
                PurchaseBuilder.aPurchase()
                        .setCreditCardToken("8312fsa938gg219fd=")
                        .setUserDocument("dusaiyudsa78dsa=")
                        .setValue(2000L).build(),
                "OzgDHDLIKJk2FDQy5xclhA==",
                "qy//miOz0SJ8gs0L3cyNuVe204t47h4TICqP5J+4iFE=",
                30L);

        Assertions.assertEquals(30L, purchase.getValue());
    }

    @Test
    @DisplayName("Should to delete a purchase with sucess")
    void deletePurchase(){

        Purchase purchase = PurchaseBuilder.aPurchase().build();

        purchaseService.deletePurchase(purchase);
        Mockito.verify(purchaseRepository, Mockito.times(1))
            .delete(Mockito.any(Purchase.class));
    }
}
