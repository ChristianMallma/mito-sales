package com.mitocode.service.interfaces;

import com.mitocode.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICategoryService extends ICRUD<Category, Integer> {

    List<Category> findByName(String name);
    List<Category> findByNameLikeIgnoreCase(String name);
    List<Category> findByNameAndEnabled(String name, boolean enabled);
    List<Category> getNameAndDescription1(String name, String desc);
    List<Category> getNameAndDescription2(String name, String desc);
    Page<Category> findPage(Pageable pageable);
    List<Category> findAllOrder(String param);

}
