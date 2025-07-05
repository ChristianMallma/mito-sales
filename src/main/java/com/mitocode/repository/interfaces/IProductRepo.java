package com.mitocode.repository.interfaces;

import com.mitocode.model.Product;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProductRepo extends IGenericRepo<Product, Integer> {

    // SQL
    // select * from Product p inner join Category c on p.id_category = c.id_category where c.name = ?;
    @Query("from Product p where p.category.name = :name")
    List<Product> getProductsByCategory(String name);

}
