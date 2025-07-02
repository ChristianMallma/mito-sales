package com.mitocode.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean // para que pueda reconocer los genéricos y no se genere un Bean (evitar el java.lang.Object)
public interface IGenericRepo<T, ID> extends JpaRepository<T, ID> {
}
