package com.mitocode.controler;

import com.mitocode.dto.ProductDTO;
import com.mitocode.model.Product;
import com.mitocode.service.interfaces.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
//@AllArgsConstructor
@RequiredArgsConstructor // Esto para solo tome en la inyección de dependencia aquellos que tienen "final"
public class ProductController {

//    @Autowired
//    @Qualifier("aliasDeLaClaseAUsar") -> Se usa cuando hay varias clases que implementan la interfaz
    private final IProductService service; // Spring busca la clase que está implementando la interfaz IProductService

    @Qualifier("defaultMapper") // Para usar el Bean con el mapper que se desea, en este caso productMapper
    private final ModelMapper modelMapper; // Spring busca la clase que está implementando la interfaz, en nuestro caso MapperConfig

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() throws Exception {
        List<ProductDTO> list = service.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable("id") Integer id) throws Exception {
        ProductDTO obj = convertToDTO(service.findById(id));
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> save(@Valid @RequestBody ProductDTO dto) throws Exception { // @Valid para que se pueda aplicar validaciones puestas en el dto
        Product newProduct = service.save(convertToEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(newProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody ProductDTO dto) throws Exception {
        Product productUpdated = service.update(id, convertToEntity(dto));
        return ResponseEntity.ok().body(convertToDTO(productUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /// query ///
    @GetMapping("/byCategory")
    public ResponseEntity<List<ProductDTO>> findByCategory(@RequestParam("category") String name) throws Exception {
        List<ProductDTO> list = service.getProductsByCategory(name).stream().map(this::convertToDTO).toList();
        return ResponseEntity.ok().body(list);
    }

    // Clases de ayuda para convertir de entity a dto o viceversa -> para no estar repitiendo constantemente mapper.map en cada endpoint
    private ProductDTO convertToDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    private Product convertToEntity(ProductDTO dto) {
        return modelMapper.map(dto, Product.class);
    }

}
