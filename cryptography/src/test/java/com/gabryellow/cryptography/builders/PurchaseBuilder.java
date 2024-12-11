package com.gabryellow.cryptography.builders;

import com.gabryellow.cryptography.entities.Purchase;

public class PurchaseBuilder {

    private Long id;
    private String userDocument;
    private String creditCardToken;
    private Long value;

    private PurchaseBuilder(){

    }

    public static PurchaseBuilder aPurchase(){
        PurchaseBuilder purchaseBuilder = new PurchaseBuilder();
        return setDefaultData(purchaseBuilder);
    }

    private static PurchaseBuilder setDefaultData(PurchaseBuilder purchaseBuilder){
        purchaseBuilder.id = 1L;
        purchaseBuilder.userDocument = "OzgDHDLIKJk2FDQy5xclhA==";
        purchaseBuilder.creditCardToken = "qy//miOz0SJ8gs0L3cyNuVe204t47h4TICqP5J+4iFE=";
        purchaseBuilder.value = 20L;

        return purchaseBuilder;
    }

    public PurchaseBuilder setId(Long id){
        this.id = id;
        return this;
    }

    public PurchaseBuilder setUserDocument(String userDocument){
        this.userDocument = userDocument;
        return this;
    }

    public PurchaseBuilder setCreditCardToken(String creditCardToken){
        this.creditCardToken = creditCardToken;
        return this;
    }

    public PurchaseBuilder setValue(Long value){
        this.value = value;
        return this;
    }

    public Purchase build(){
        return new Purchase(this.id, this.userDocument, this.creditCardToken, this.value);
    }
}
