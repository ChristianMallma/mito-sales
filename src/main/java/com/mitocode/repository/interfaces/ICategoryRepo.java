package com.mitocode.repository.interfaces;

import com.mitocode.model.Category;

// @Repository -> ya no es necesario, porque JpaRepository ya lo implementa
public interface ICategoryRepo extends IGenericRepo<Category, Integer> { // <Entidad, tipo de la llave primaria de la entidad>

//    Category save(Category category);

}
