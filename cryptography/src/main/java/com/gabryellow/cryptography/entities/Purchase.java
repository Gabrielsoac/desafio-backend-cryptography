package com.gabryellow.cryptography.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "purchase")
public class Purchase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_document", nullable = false)
    private String userDocument;

    @Column(name = "credit_card_token", nullable = false)
    private String creditCardToken;

    @Column(nullable = false)
    private Long value;

    public Purchase(String userDocument, String creditCardToken, Long value) {
        this.userDocument = userDocument;
        this.creditCardToken = creditCardToken;
        this.value = value;
    }

    public Purchase(){

    }

    public Long getId() {
        return id;
    }

    public String getUserDocument() {
        return userDocument;
    }

    public void setUserDocument(String userDocument) {
        this.userDocument = userDocument;
    }

    public String getCreditCardToken() {
        return creditCardToken;
    }

    public void setCreditCardToken(String creditCardToken) {
        this.creditCardToken = creditCardToken;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Objects.equals(id, purchase.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
