package com.mitocode.exception;

import java.time.LocalDateTime;

// Se usará un Record, porque se espera que cuando se muestre el error al cliente, nadie cambiará su valor luego (inmutable)
public record CustomErrorResponse(
        LocalDateTime datetime,
        String message,
        String path
) {
}
