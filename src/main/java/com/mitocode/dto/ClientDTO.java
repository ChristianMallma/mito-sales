package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDTO {

    private Integer idClient;

    @NotNull
    private String  firstNameClient;

    @NotNull
    private String  lastNameClient;

    @NotNull
    private String  countryClient;

    @NotNull
    private String  cardIdClient;

    @NotNull
    private String  phoneNumberClient;

    @NotNull
    private String  emailClient;

    @NotNull
    private String  addressClient;
}
