package com.mitocode.service.impl;

import com.mitocode.model.Role;
import com.mitocode.repository.interfaces.IGenericRepo;
import com.mitocode.repository.interfaces.IRoleRepo;
import com.mitocode.service.interfaces.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Esto para solo acepte en la inyección aquellos que tienen "final"
public class RoleServiceImpl extends CRUDImpl<Role, Integer> implements IRoleService {

    private final IRoleRepo repo; // Al heredar JpaRepository, Spring asume que necesitas una instancia aquí para persistir en db

    @Override
    protected IGenericRepo<Role, Integer> getRepo() {
        return repo;
    }

}
