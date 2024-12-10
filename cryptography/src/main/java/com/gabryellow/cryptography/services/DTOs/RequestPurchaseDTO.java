package com.gabryellow.cryptography.services.DTOs;

public record RequestPurchaseDTO(String userDocument, String creditCardToken, Long value) {
}
