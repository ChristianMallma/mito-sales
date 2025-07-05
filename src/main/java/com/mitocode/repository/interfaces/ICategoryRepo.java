package com.mitocode.repository.interfaces;

import com.mitocode.model.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

// @Repository -> ya no es necesario, porque JpaRepository ya lo implementa
public interface ICategoryRepo extends IGenericRepo<Category, Integer> { // <Entidad, tipo de la llave primaria de la entidad>

    // DerivedQuery
    // SELECT * FROM Category c where c.name=?
    List<Category> findByName(String name);

    // SELECT * FROM Category c where c.name LIKE '%abc%';
    List<Category> findByNameLikeIgnoreCase(String name);
    //%XYZ% -> findByNameContains
    //%XYZ -> findByNameStartsWith
    //XYZ% -> findByNameEndsWith
    List<Category> findByNameAndEnabled(String name, boolean enabled);
    List<Category> findByNameOrEnabled(String name, boolean enabled);
    List<Category> findByEnabled(boolean enabled);
    List<Category> findByEnabledTrue();
    List<Category> findByEnabledFalse();
    Optional<Category> findOneByName(String name);
    List<Category> findTop3ByName(String name);
    List<Category> findByNameIs(String name);
    List<Category> findByNameIsNot(String name);
    List<Category> findByNameIsNull();
    List<Category> findByNameIsNotNull();
    List<Category> findByNameEqualsIgnoreCase(String name);
    List<Category> findByIdCategoryLessThan(Integer idCategory);
    List<Category> findByIdCategoryLessThanEqual(Integer idCategory);
    List<Category> findByIdCategoryGreaterThan(Integer idCategory);
    List<Category> findByIdCategoryGreaterThanEqual(Integer idCategory);
    List<Category> findByIdCategoryBetween(Integer idCategory1, Integer idCategory2);
    List<Category> findByNameOrderByDescription(String name);
    List<Category> findByNameOrderByDescriptionDesc(String name);
    List<Category> findByNameOrderByDescriptionAsc(String name);

    // JPQL -> Java Persistence Query Language
    @Query("FROM Category c where c.name = :name and c.description like %:desc%")
    List<Category> getNameAndDescription1(@Param("name") String name, @Param("desc") String desc);

    @Query("select new Category(c.name, c.enabled) FROM Category c where c.name = :name and c.description like %:desc%")
    List<Category> getNameAndDescription2(@Param("name") String name, @Param("desc") String desc);

    // SQL -> NativeQuery
    @Query(value = "select * from category c where c.name = :name", nativeQuery = true)
    List<Category> getNameSQL(@Param("name") String name);

    @Modifying // Esto se agregará siempre que haya modificación (insert, update, delete)
    @Query(value = "update category set name = :name", nativeQuery = true)
    Integer updateNames(@Param("name") String name);
}
