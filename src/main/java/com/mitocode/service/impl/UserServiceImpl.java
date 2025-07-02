package com.mitocode.service.impl;

import com.mitocode.model.User;
import com.mitocode.repository.interfaces.IGenericRepo;
import com.mitocode.repository.interfaces.IUserRepo;
import com.mitocode.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Esto para solo acepte en la inyección aquellos que tienen "final"
public class UserServiceImpl extends CRUDImpl<User, Integer> implements IUserService {

    private final IUserRepo repo; // Al heredar JpaRepository, Spring asume que necesitas una instancia aquí para persistir en db

    @Override
    protected IGenericRepo<User, Integer> getRepo() {
        return repo;
    }

}
