package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {

    private Integer idCategory;

    @NotNull
    @Size(min = 3, max = 50)
    private  String nameOfCategory;

    @NotNull
    @Size(min = 3, max = 150)
    private  String descriptionCategory;

    @NotNull
    private boolean enabledCategory;
}
