package com.mitocode.service.impl;

import com.mitocode.model.Product;
import com.mitocode.repository.interfaces.IProductRepo;
import com.mitocode.repository.interfaces.IGenericRepo;
import com.mitocode.service.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // Esto para solo acepte en la inyección aquellos que tienen "final"
public class ProductServiceImpl extends CRUDImpl<Product, Integer> implements IProductService {

    private final IProductRepo repo; // Al heredar JpaRepository, Spring asume que necesitas una instancia aquí para persistir en db

    @Override
    protected IGenericRepo<Product, Integer> getRepo() {
        return repo;
    }

    @Override
    public List<Product> getProductsByCategory(String name) {
        return repo.getProductsByCategory(name);
    }
}
