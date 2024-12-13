package com.gabryellow.cryptography.config;

import java.time.Instant;
import java.util.List;

public record APIRestError(
        Instant timestamp,
        int code,
        String name,
        String error) {
}
