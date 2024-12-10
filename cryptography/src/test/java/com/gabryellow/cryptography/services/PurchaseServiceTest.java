package com.gabryellow.cryptography.services;

import com.gabryellow.cryptography.repository.PurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PurchaseServiceTest {

    @BeforeEach
    void setUp(){
        PurchaseRepository purchaseRepository = Mockito.mock(PurchaseRepository.class);
        PurchaseServiceImpl purchaseService = new PurchaseServiceImpl(purchaseRepository);
    }

    @Test
    @DisplayName("Should get Purchase with sucess")
    void getPurchaseWithSucess(){

        


    }

}
