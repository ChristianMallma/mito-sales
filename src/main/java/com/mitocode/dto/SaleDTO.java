package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaleDTO {

    private Integer idSale;

    @NotNull
    @JsonIncludeProperties(value = { "idClient" })
    private ClientDTO client;

    @NotNull
    @JsonIncludeProperties(value = { "idUser" })
    private UserDTO user;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Min(value = 1)
    private double total;

    @NotNull
    @Min(value = 0)
    private double tax;

    @NotNull
    private boolean enabled;

    @NotNull
    @JsonManagedReference // Para que no sea necesario colocar en la inserción el bucle de (private SaleDTO sale;) que viene en SaleDetailDTO
    private List<SaleDetailDTO> details;
}
