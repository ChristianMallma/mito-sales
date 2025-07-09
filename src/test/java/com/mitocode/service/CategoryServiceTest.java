package com.mitocode.service;

import com.mitocode.dto.CategoryDTO;
import com.mitocode.model.Category;
import com.mitocode.repository.interfaces.ICategoryRepo;
import com.mitocode.service.impl.CategoryServiceImpl;
import com.mitocode.service.interfaces.ICategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {

    @MockitoBean
    private ICategoryRepo repo;

    @MockitoBean
    private ICategoryService service;

    private Category CATEGORY_1;
    private Category CATEGORY_2;
    private Category CATEGORY_3;

    @BeforeEach
    public void setUp() { //previo antes que se ejecute la lógica (inicializar valores)
        MockitoAnnotations.openMocks(this);
        this.service = new CategoryServiceImpl(repo);

        CATEGORY_1 = new Category(1, "TV", "Television", true);
        CATEGORY_2 = new Category(2, "PSP", "Play Station Portable", true);
        CATEGORY_3 = new Category(3, "BOOKS", "Some Books", true);

        Mockito.when(repo.findAll()).thenReturn(List.of(CATEGORY_1, CATEGORY_2, CATEGORY_3));
        Mockito.when(repo.findById(any())).thenReturn(Optional.of(CATEGORY_1));
        Mockito.when(repo.save(any())).thenReturn(CATEGORY_1);

    }

    @Test
    void findAllTest() throws Exception {
        List<Category> response = service.findAll();

        assertNotNull(response);
//        assertTrue(response.isEmpty());
//        assertFalse(response.isEmpty());
    }

    @Test
    void findByIdTest() throws Exception {
        final int ID = 1;
        Category response = service.findById(ID);

        assertNotNull(response);
    }

    @Test
    void saveTest() throws Exception {
        Category response = service.save(CATEGORY_1);
        assertNotNull(response);
    }

    @Test
    void deleteTest() throws Exception {
        final int ID = 1;

        service.delete(ID);
        service.delete(ID);
        service.delete(ID);

//        verify(repo, times(3)).deleteById(ID);
//        verify(repo, atLeast(2)).deleteById(ID);
        verify(repo, atMost(3)).deleteById(ID);
    }
}
