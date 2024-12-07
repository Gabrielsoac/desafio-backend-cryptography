package com.gabryellow.cryptography.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "purchase")
public class Purchase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_document", nullable = false)
    private byte[] userDocument;

    @Column(name = "credit_card_token", nullable = false)
    private byte[] creditCardToken;

    @Column(nullable = false)
    private Long value;

    public Purchase(byte[] userDocument, byte[] creditCardToken, Long value) {
        this.userDocument = userDocument;
        this.creditCardToken = creditCardToken;
        this.value = value;
    }

    public Purchase(){

    }

    public Long getId() {
        return id;
    }

    public byte[] getUserDocument() {
        return userDocument;
    }

    public void setUserDocument(byte[] userDocument) {
        this.userDocument = userDocument;
    }

    public byte[] getCreditCardToken() {
        return creditCardToken;
    }

    public void setCreditCardToken(byte[] creditCardToken) {
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
