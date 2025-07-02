package com.mitocode.controler;

import com.mitocode.dto.ProviderDTO;
import com.mitocode.model.Provider;
import com.mitocode.service.interfaces.IProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/providers")
//@AllArgsConstructor
@RequiredArgsConstructor // Esto para solo tome en la inyección de dependencia aquellos que tienen "final"
public class ProviderController {

//    @Autowired
//    @Qualifier("aliasDeLaClaseAUsar") -> Se usa cuando hay varias clases que implementan la interfaz
    private final IProviderService service; // Spring busca la clase que está implementando la interfaz IProviderService

    @Qualifier("defaultMapper") // Para usar el Bean con el mapper que se desea, en este caso providerMapper
    private final ModelMapper modelMapper; // Spring busca la clase que está implementando la interfaz, en nuestro caso MapperConfig

    @GetMapping
    public ResponseEntity<List<ProviderDTO>> findAll() throws Exception {
        List<ProviderDTO> list = service.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderDTO> findById(@PathVariable("id") Integer id) throws Exception {
        ProviderDTO obj = convertToDTO(service.findById(id));
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<ProviderDTO> save(@Valid @RequestBody ProviderDTO dto) throws Exception { // @Valid para que se pueda aplicar validaciones puestas en el dto
        Provider newProvider = service.save(convertToEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(newProvider));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody ProviderDTO dto) throws Exception {
        Provider providerUpdated = service.update(id, convertToEntity(dto));
        return ResponseEntity.ok().body(convertToDTO(providerUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Clases de ayuda para convertir de entity a dto o viceversa -> para no estar repitiendo constantemente mapper.map en cada endpoint
    private ProviderDTO convertToDTO(Provider provider) {
        return modelMapper.map(provider, ProviderDTO.class);
    }

    private Provider convertToEntity(ProviderDTO dto) {
        return modelMapper.map(dto, Provider.class);
    }

}
