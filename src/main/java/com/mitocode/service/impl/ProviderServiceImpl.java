package com.mitocode.service.impl;

import com.mitocode.model.Provider;
import com.mitocode.repository.interfaces.IGenericRepo;
import com.mitocode.repository.interfaces.IProviderRepo;
import com.mitocode.service.interfaces.IProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Esto para solo acepte en la inyección aquellos que tienen "final"
public class ProviderServiceImpl extends CRUDImpl<Provider, Integer> implements IProviderService {

    private final IProviderRepo repo; // Al heredar JpaRepository, Spring asume que necesitas una instancia aquí para persistir en db

    @Override
    protected IGenericRepo<Provider, Integer> getRepo() {
        return repo;
    }

}
