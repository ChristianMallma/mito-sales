package com.mitocode.controler;

import com.mitocode.dto.IProcedureSaleDTO;
import com.mitocode.dto.ProcedureSaleDTO;
import com.mitocode.dto.SaleDTO;
import com.mitocode.model.Sale;
import com.mitocode.service.interfaces.ISaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sales")
//@AllArgsConstructor
@RequiredArgsConstructor // Esto para solo tome en la inyección de dependencia aquellos que tienen "final"
public class SaleController {

//    @Autowired
//    @Qualifier("aliasDeLaClaseAUsar") -> Se usa cuando hay varias clases que implementan la interfaz
    private final ISaleService service; // Spring busca la clase que está implementando la interfaz ISaleService

    @Qualifier("defaultMapper") // Para usar el Bean con el mapper que se desea, en este caso saleMapper
    private final ModelMapper modelMapper; // Spring busca la clase que está implementando la interfaz, en nuestro caso MapperConfig

    @GetMapping
    public ResponseEntity<List<SaleDTO>> findAll() throws Exception {
        List<SaleDTO> list = service.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> findById(@PathVariable("id") Integer id) throws Exception {
        SaleDTO obj = convertToDTO(service.findById(id));
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<SaleDTO> save(@Valid @RequestBody SaleDTO dto) throws Exception { // @Valid para que se pueda aplicar validaciones puestas en el dto
        Sale newSale = service.save(convertToEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(newSale));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody SaleDTO dto) throws Exception {
        Sale saleUpdated = service.update(id, convertToEntity(dto));
        return ResponseEntity.ok().body(convertToDTO(saleUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /// queries ///////////////
    @GetMapping("/mostexpensive")
    public ResponseEntity<SaleDTO> findMostExpensive() throws Exception {
        Sale obj = service.getSaleMostExpensive();
        return ResponseEntity.ok().body(convertToDTO(obj));
    }

    @GetMapping("/bestseller")
    public ResponseEntity<String> getBestSeller() throws Exception {
        String username = service.getBestSellerUsername();
        return ResponseEntity.ok().body(username);
    }

    @GetMapping("/sellercount")
    public ResponseEntity<Map<String, Long>> getSaleCountBySeller() throws Exception {
        return ResponseEntity.ok().body(service.getSaleCountBySeller());
    }

    @GetMapping("/bestproduct")
    public ResponseEntity<Map<String, Double>> getBestProduct() throws Exception {
        Map<String, Double> byProduct = service.getMostSellerProduct();
        return ResponseEntity.ok().body(byProduct);
    }

    @GetMapping("/resume1")
    public ResponseEntity<List<ProcedureSaleDTO>> getResume1() throws Exception {
        return ResponseEntity.ok(service.callProcedure1());
    }

    @GetMapping("/resume2")
    public ResponseEntity<List<IProcedureSaleDTO>> getResume2() throws Exception {
        return ResponseEntity.ok(service.callProcedure2());
    }

    @GetMapping("/resume3")
    public ResponseEntity<List<ProcedureSaleDTO>> getResume3() throws Exception {
        return ResponseEntity.ok(service.callProcedure3());
    }

    @GetMapping("/resume4")
    public ResponseEntity<Void> getResume4() throws Exception {
        service.callProcedure4();
        return ResponseEntity.ok().build();
    }

    // Clases de ayuda para convertir de entity a dto o viceversa -> para no estar repitiendo constantemente mapper.map en cada endpoint
    private SaleDTO convertToDTO(Sale sale) {
        return modelMapper.map(sale, SaleDTO.class);
    }

    private Sale convertToEntity(SaleDTO dto) {
        return modelMapper.map(dto, Sale.class);
    }

}
