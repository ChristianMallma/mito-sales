package com.mitocode.service.impl;

import com.mitocode.model.Category;
import com.mitocode.repository.interfaces.ICategoryRepo;
import com.mitocode.repository.interfaces.IGenericRepo;
import com.mitocode.service.interfaces.ICRUD;
import com.mitocode.service.interfaces.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public List<Category> findByName(String name) {
        return repo.findByName(name);
    }

    @Override
    public List<Category> findByNameLikeIgnoreCase(String name) {
        return repo.findByNameLikeIgnoreCase("%"+name+"%");
    }

    @Override
    public List<Category> findByNameAndEnabled(String name, boolean enabled) {
        return repo.findByNameAndEnabled(name, enabled);
    }

    @Override
    public List<Category> getNameAndDescription1(String name, String desc) {
        return repo.getNameAndDescription1(name, desc);
    }

    @Override
    public List<Category> getNameAndDescription2(String name, String desc) {
        return repo.getNameAndDescription2(name, desc);
    }

    @Override
    public Page<Category> findPage(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public List<Category> findAllOrder(String param) {
        Sort.Direction direction = param.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return repo.findAll(Sort.by(direction, "name"));
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
