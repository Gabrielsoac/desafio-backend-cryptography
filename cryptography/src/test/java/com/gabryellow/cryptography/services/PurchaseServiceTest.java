package com.gabryellow.cryptography.services;

import com.gabryellow.cryptography.builders.PurchaseBuilder;
import com.gabryellow.cryptography.entities.Purchase;
import com.gabryellow.cryptography.repository.PurchaseRepository;
import com.gabryellow.cryptography.services.DTOs.ResponsePurchaseDTO;
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
}
