package com.mitocode.repository.interfaces;

import com.mitocode.model.User;

public interface IUserRepo extends IGenericRepo<User, Integer> {

    // Query para retornar la información de un usuario a través de su username
    // From User where u.username = :username
    User findOneByUsername(String username);

}
