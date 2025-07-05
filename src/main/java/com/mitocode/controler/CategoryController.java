package com.mitocode.controler;

import com.mitocode.dto.CategoryDTO;
import com.mitocode.model.Category;
import com.mitocode.service.interfaces.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
//@AllArgsConstructor
@RequiredArgsConstructor // Esto para solo tome en la inyección de dependencia aquellos que tienen "final"
public class CategoryController {

//    @Autowired
//    @Qualifier("aliasDeLaClaseAUsar") -> Se usa cuando hay varias clases que implementan la interfaz
    private final ICategoryService service; // Spring busca la clase que está implementando la interfaz ICategoryService

    @Qualifier("categoryMapper") // Para usar el Bean con el mapper que se desea, en este caso categoryMapper
    private final ModelMapper modelMapper; // Spring busca la clase que está implementando la interfaz, en nuestro caso MapperConfig

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() throws Exception {
//        ModelMapper mapper = new ModelMapper();
        List<CategoryDTO> list = service.findAll()
                .stream()
                .map(this::convertToDTO)
//                .map(e -> modelMapper.map(e, CategoryDTO.class))
//                .map(e -> new CategoryDTO(e.getIdCategory(), e.getName(), e.getDescription(), e.isEnabled()))
                .toList();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable("id") Integer id) throws Exception {
        CategoryDTO obj = convertToDTO(service.findById(id));
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> save(@Valid @RequestBody CategoryDTO dto) throws Exception { // @Valid para que se pueda aplicar validaciones puestas en el dto
        Category newCategory = service.save(convertToEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(newCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody CategoryDTO dto) throws Exception {
        Category categoryUpdated = service.update(id, convertToEntity(dto));
        return ResponseEntity.ok().body(convertToDTO(categoryUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /// //////////// queries ///////////////
    @GetMapping("find/name/{name}")
    public ResponseEntity<List<CategoryDTO>> findByName(@PathVariable("name") String name) throws Exception {
        List<CategoryDTO> list = service.findByName(name).stream().map(this::convertToDTO).toList();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("find/name/like/{name}")
    public ResponseEntity<List<CategoryDTO>> findByNameLike(@PathVariable("name") String name) throws Exception {
        List<CategoryDTO> list = service.findByNameLikeIgnoreCase(name).stream().map(this::convertToDTO).toList();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("find/name/enabled")
    public ResponseEntity<List<CategoryDTO>> findByNameEnabled(@RequestParam("name") String name, @RequestParam("enabled") boolean enabled) throws Exception {
        List<CategoryDTO> list = service.findByNameAndEnabled(name, enabled).stream().map(this::convertToDTO).toList();
        return ResponseEntity.ok().body(list);
    }

    ////////// JPQL /////////
    @GetMapping("get/name/desc1")
    public ResponseEntity<List<CategoryDTO>> findByNameDesc1(@RequestParam("name") String name, @RequestParam("desc") String desc) throws Exception {
        List<CategoryDTO> list = service.getNameAndDescription1(name, desc).stream().map(this::convertToDTO).toList();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("get/name/desc2")
    public ResponseEntity<List<CategoryDTO>> findByNameDesc2(@RequestParam("name") String name, @RequestParam("desc") String desc) throws Exception {
        List<CategoryDTO> list = service.getNameAndDescription2(name, desc).stream().map(this::convertToDTO).toList();
        return ResponseEntity.ok().body(list);
    }


    // Clases de ayuda para convertir de entity a dto o viceversa -> para no estar repitiendo constantemente mapper.map en cada endpoint
    private CategoryDTO convertToDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    private Category convertToEntity(CategoryDTO dto) {
        return modelMapper.map(dto, Category.class);
    }






    // TODO: Esto fue para explicar puntos teóricos
    // Cuando es por constructor, Spring en automático reconoce que es como un autowired
//    public CategoryController(ICategoryService service) {
//        this.service = service;
//    }

//    @GetMapping
//    public Category save() {
////        service = new CategoryService();
//        return service.validAndSave( new Category(1, "COMPUTERS", "Some computers", true) );
//    }

}
