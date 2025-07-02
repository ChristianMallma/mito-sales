package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    private Integer idProduct;

    @NotNull
    private Integer idCategory;

    @NotNull
    private String nameProduct;

    @NotNull
    private String descriptionProduct;

    @NotNull
    @Min(value = 1)
    @Max(value = 9999)
    private double priceProduct;

    @NotNull
    private boolean enabledProduct;
}
