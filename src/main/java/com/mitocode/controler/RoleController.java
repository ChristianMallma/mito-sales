package com.mitocode.controler;

import com.mitocode.dto.RoleDTO;
import com.mitocode.model.Role;
import com.mitocode.service.interfaces.IRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
//@AllArgsConstructor
@RequiredArgsConstructor // Esto para solo tome en la inyección de dependencia aquellos que tienen "final"
public class RoleController {

//    @Autowired
//    @Qualifier("aliasDeLaClaseAUsar") -> Se usa cuando hay varias clases que implementan la interfaz
    private final IRoleService service; // Spring busca la clase que está implementando la interfaz IRoleService

    @Qualifier("defaultMapper") // Para usar el Bean con el mapper que se desea, en este caso roleMapper
    private final ModelMapper modelMapper; // Spring busca la clase que está implementando la interfaz, en nuestro caso MapperConfig

    @GetMapping
    public ResponseEntity<List<RoleDTO>> findAll() throws Exception {
        List<RoleDTO> list = service.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> findById(@PathVariable("id") Integer id) throws Exception {
        RoleDTO obj = convertToDTO(service.findById(id));
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<RoleDTO> save(@Valid @RequestBody RoleDTO dto) throws Exception { // @Valid para que se pueda aplicar validaciones puestas en el dto
        Role newRole = service.save(convertToEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(newRole));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody RoleDTO dto) throws Exception {
        Role roleUpdated = service.update(id, convertToEntity(dto));
        return ResponseEntity.ok().body(convertToDTO(roleUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Clases de ayuda para convertir de entity a dto o viceversa -> para no estar repitiendo constantemente mapper.map en cada endpoint
    private RoleDTO convertToDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }

    private Role convertToEntity(RoleDTO dto) {
        return modelMapper.map(dto, Role.class);
    }

}
