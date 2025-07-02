package com.mitocode.service.impl;

import com.mitocode.model.Sale;
import com.mitocode.repository.interfaces.IGenericRepo;
import com.mitocode.repository.interfaces.ISaleRepo;
import com.mitocode.service.interfaces.ISaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Esto para solo acepte en la inyección aquellos que tienen "final"
public class SaleServiceImpl extends CRUDImpl<Sale, Integer> implements ISaleService {

    private final ISaleRepo repo; // Al heredar JpaRepository, Spring asume que necesitas una instancia aquí para persistir en db

    @Override
    protected IGenericRepo<Sale, Integer> getRepo() {
        return repo;
    }

}
