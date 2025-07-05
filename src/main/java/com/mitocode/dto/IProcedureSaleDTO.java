package com.mitocode.dto;

// JPA Projection
public interface IProcedureSaleDTO {

    // Al escribir de la forma getName -> spring automaticamente crea la clas compatible con esta interfaz
    Integer getQuantityFn();
    String getDatetimeFn();
}
