package com.mitocode.controler;

import com.mitocode.dto.CategoryDTO;
import com.mitocode.security.JwtRequest;
import com.mitocode.security.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class RestTemplateController {

    private final RestTemplate restTemplate;

    @GetMapping("pokemon/{name}")
    public ResponseEntity<String> getPokemon(@PathVariable("name") String name) {
        String pokemonUrl = "https://pokeapi.co/api/v2/pokemon/" + name;
        String response = restTemplate.getForEntity(pokemonUrl, String.class).getBody();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/test1")
    public ResponseEntity<List<CategoryDTO>> test1() {
        String url = "http://localhost:8080/categories";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ParameterizedTypeReference<List<CategoryDTO>> typeRef = new ParameterizedTypeReference<>() {}; // para especificar el tipo de retorno

        return restTemplate.exchange(url, HttpMethod.GET, entity, typeRef);
    }

    @GetMapping("/test2")
    public ResponseEntity<String> test2(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size
    ) {
        String url = "http://localhost:8080/categories/pagination2?p=" + page + "&s=" + size;
        String response = restTemplate.getForEntity(url, String.class).getBody();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/test3")
    public ResponseEntity<String> test3(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size
    ) {
        String url = "http://localhost:8080/categories/pagination2?p={page}&s={size}";

        Map<String, Integer> uriVariables = new HashMap<>();
        uriVariables.put("page", page);
        uriVariables.put("size", size);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        return  restTemplate.exchange(url, HttpMethod.GET, entity, String.class, uriVariables);
    }

    @PostMapping("/test4")
    public ResponseEntity<CategoryDTO> test4(@RequestBody CategoryDTO dto) {
        String url = "http://localhost:8080/categories";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CategoryDTO> entity = new HttpEntity<>(dto, headers);
        CategoryDTO response = restTemplate.postForObject(url, entity, CategoryDTO.class);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/test5/{id}")
    public ResponseEntity<CategoryDTO> test5(@PathVariable("id") int id, @RequestBody CategoryDTO dto) {
        String url = "http://localhost:8080/categories/" + id;
        HttpEntity<CategoryDTO> entity = new HttpEntity<>(dto);

        return restTemplate.exchange(url, HttpMethod.PUT, entity, CategoryDTO.class);
    }

    @DeleteMapping("/test6/{id}")
    public ResponseEntity<Void> test6(@PathVariable("id") int id) {
        String url = "http://localhost:8080/categories/" + id;

        restTemplate.delete(url);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/test7/{id}")
    public ResponseEntity<Void> test7(@PathVariable("id") int id) {
        String url = "http://localhost:8080/categories/{idCategory}";

        Map<String, Integer> uriVariables = new HashMap<>();
        uriVariables.put("idCategory", id);

        restTemplate.delete(url, uriVariables);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/test8/{id}")
    public ResponseEntity<Void> test8(@PathVariable("id") int id, @RequestBody JwtRequest request) {
        String token = generateToken(request);

        // Enviar token para pedir o hacer acciones
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = "http://localhost:8080/categories/{idCategory}";

        Map<String, Integer> uriVariables = new HashMap<>();
        uriVariables.put("idCategory", id);

        restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class, uriVariables);

        return ResponseEntity.noContent().build();

    }

    private String generateToken(JwtRequest userRequest) {
        final String AUTHENTICATION_URL = "http://localhost:8080/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<JwtRequest> authEntity = new HttpEntity<>(userRequest, headers);
        ResponseEntity<JwtResponse> response = restTemplate.exchange(AUTHENTICATION_URL, HttpMethod.POST, authEntity, JwtResponse.class);

        if(response.getBody() != null) {
            return response.getBody().accessToken();
        }

        return "INVALID_TOKEN";
    }
}
