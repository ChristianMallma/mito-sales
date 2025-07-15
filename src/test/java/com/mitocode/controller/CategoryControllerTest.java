package com.mitocode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitocode.controler.CategoryController;
import com.mitocode.dto.CategoryDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Category;
import com.mitocode.service.interfaces.ICategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ICategoryService service; // Simulando el servicio de ICategoryService que se necesita para invocar findAll del servicio

    @MockitoBean
    @Qualifier("categoryMapper")
    private ModelMapper modelMapper; // Simulando el mapper que se necesita para convertir (entity/dto)

    @Autowired
    private ObjectMapper objectMapper;

    Category CATEGORY_1 = new Category(1, "TV", "Television", true);
    Category CATEGORY_2 = new Category(2, "PSP", "Play Station Portable", true);
    Category CATEGORY_3 = new Category(3, "BOOKS", "Some Books", true);

    CategoryDTO CATEGORYDTO_1 = new CategoryDTO(1, "TV", "Television", true);
    CategoryDTO CATEGORYDTO_2 = new CategoryDTO(2, "PSP", "Play Station Portable", true);
    CategoryDTO CATEGORYDTO_3 = new CategoryDTO(3, "BOOKS", "Some Books", true);

    @Test
    public void findAllTest() throws Exception {
        // Asumir que cuando se llame al findAll(), el retorno sea la lista formada por las categorías ficticias creadas por nosotros
        Mockito.when(service.findAll()).thenReturn(Arrays.asList(CATEGORY_1, CATEGORY_2, CATEGORY_3));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

    }

    @Test
    public void findByIdTest() throws Exception {
        final int ID = 1;

        Mockito.when(service.findById(ID)).thenReturn(CATEGORY_1);
        Mockito.when(modelMapper.map(CATEGORY_1, CategoryDTO.class)).thenReturn(CATEGORYDTO_1);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/categories/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameOfCategory", is("TV")));
    }

    @Test
    public void sateTest() throws Exception {
        Mockito.when(service.save(any())).thenReturn(CATEGORY_3);
        Mockito.when(modelMapper.map(CATEGORY_3, CategoryDTO.class)).thenReturn(CATEGORYDTO_3);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CATEGORYDTO_3))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.enabledCategory", is(true)));
    }

    @Test
    public void updateTest() throws Exception {
        Mockito.when(service.update(any(), any())).thenReturn(CATEGORY_2);
        Mockito.when(modelMapper.map(CATEGORY_2, CategoryDTO.class)).thenReturn(CATEGORYDTO_2);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/categories/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CATEGORYDTO_2))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.enabledCategory", is(true)));
    }

    @Test
    public void updateErrorTest() throws Exception {
        final int ID = 99;
        Mockito.when(service.update(any(), any())).thenThrow(new ModelNotFoundException("ID NOT VALID: " + ID));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/categories/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CATEGORYDTO_2))
                )
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(ModelNotFoundException.class, result.getResolvedException()));
    }

    @Test
    public void deleteTest() throws Exception {
        final int ID = 1;

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/categories/{id}", ID)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteErrorTest() throws Exception {
        final int ID = 99;

        Mockito.doThrow(new ModelNotFoundException("ID NOT VALID: " + ID)).when(service).delete(any());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/categories/{id}", ID)
                )
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(ModelNotFoundException.class, result.getResolvedException()));
    }
}
