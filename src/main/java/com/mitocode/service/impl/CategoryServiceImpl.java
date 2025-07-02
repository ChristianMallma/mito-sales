package com.mitocode.service.impl;

import com.mitocode.model.Category;
import com.mitocode.repository.interfaces.ICategoryRepo;
import com.mitocode.repository.interfaces.IGenericRepo;
import com.mitocode.service.interfaces.ICRUD;
import com.mitocode.service.interfaces.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@AllArgsConstructor // Esto asume el constructor
@RequiredArgsConstructor // Esto para solo acepte en la inyección aquellos que tienen "final"
//public class CategoryServiceImpl implements ICategoryService {
public class CategoryServiceImpl extends CRUDImpl<Category, Integer> implements ICategoryService {

//    @Autowired
    private final ICategoryRepo repo; // Al heredar JpaRepository, Spring asume que necesitas una instancia aquí para persistir en db

    @Override
    protected IGenericRepo<Category, Integer> getRepo() {
        return repo;
    }

//    @Override
//    public Category save(Category category) throws Exception {
//        return repo.save(category);
//    }
//
//    @Override
//    public Category update(Integer id, Category category) throws Exception {
//        category.setIdCategory(id);
//        return repo.save(category);
//    }
//
//    @Override
//    public List<Category> findAll() throws Exception {
//        return repo.findAll();
//    }
//
//    @Override
//    public Category findById(Integer id) throws Exception {
//        return repo.findById(id).orElse(new Category());
//    }
//
//    @Override
//    public void delete(Integer id) throws Exception {
//        repo.deleteById(id);
//    }

}
