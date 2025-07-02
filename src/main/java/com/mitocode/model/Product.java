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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idProduct;

    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false, foreignKey = @ForeignKey(name = "fk_product_category"))
    private Category category;

    @Column(nullable = false, length = 50)
    private String  name;

    @Column(nullable = false, columnDefinition = "decimal(6,2)") // 9999.99
    private Double price;

    @Column(nullable = false, length = 150)
    private String  description;

    @Column(nullable = false)
    private boolean enabled;
}
