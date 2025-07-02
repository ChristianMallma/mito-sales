package com.mitocode.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Table(name = "tbl_category")  -> para darle otro nombre en la base de datos
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category { // Esto se convierte en una tabla de base de datos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idCategory;

    @Column(nullable = false, length = 50) //, name = "category_name")
    private  String name;

    @Column(nullable = false, length = 150)
    private  String description;

    @Column(nullable = false)
    private boolean enabled;

}
