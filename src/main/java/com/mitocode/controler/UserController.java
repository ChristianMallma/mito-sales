package com.mitocode.controler;

import com.mitocode.dto.UserDTO;
import com.mitocode.model.User;
import com.mitocode.service.interfaces.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
//@AllArgsConstructor
@RequiredArgsConstructor // Esto para solo tome en la inyección de dependencia aquellos que tienen "final"
public class UserController {

//    @Autowired
//    @Qualifier("aliasDeLaClaseAUsar") -> Se usa cuando hay varias clases que implementan la interfaz
    private final IUserService service; // Spring busca la clase que está implementando la interfaz IUserService

    @Qualifier("defaultMapper") // Para usar el Bean con el mapper que se desea, en este caso userMapper
    private final ModelMapper modelMapper; // Spring busca la clase que está implementando la interfaz, en nuestro caso MapperConfig

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() throws Exception {
        List<UserDTO> list = service.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Integer id) throws Exception {
        UserDTO obj = convertToDTO(service.findById(id));
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<UserDTO> save(@Valid @RequestBody UserDTO dto) throws Exception { // @Valid para que se pueda aplicar validaciones puestas en el dto
        User newUser = service.save(convertToEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(newUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody UserDTO dto) throws Exception {
        User userUpdated = service.update(id, convertToEntity(dto));
        return ResponseEntity.ok().body(convertToDTO(userUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Clases de ayuda para convertir de entity a dto o viceversa -> para no estar repitiendo constantemente mapper.map en cada endpoint
    private UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private User convertToEntity(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }

}
