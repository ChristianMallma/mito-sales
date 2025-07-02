package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Integer idUser;

    @JsonIncludeProperties(value = { "idRole", "nameRole" }) // para seleccionar los campos que solo requiramos
    @NotNull
    private RoleDTO role;

//    @JsonProperty(value="user_name") // Si es que se requiere que aparezca de otro nombre para el cliente
    @NotNull
    private String username;

    // @JsonIgnore -> sirve para ignorar el campo tanto para lectura como para escritura
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // ignora en la respuesta al momento de escribirlo
    @NotNull
    @Size(min = 5, max = 60)
    private String password;

    @NotNull
    private boolean enabled;
}
