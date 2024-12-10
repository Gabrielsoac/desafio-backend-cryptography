package com.gabryellow.cryptography.services.DTOs;

public record ResponsePurchaseDTO(Long id,
                                  String userDocument,
                                  String creditCardToken,
                                  Long value) {
}
