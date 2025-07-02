package com.mitocode.config;

import com.mitocode.dto.CategoryDTO;
import com.mitocode.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
El fin de esta clase es poder crear un Bean de ModelMapper y poderlo usar a través de inyección de dependencias en algún lugar.
Se hace así, ya que es una librería externa, no es de nuestra autoría!
*/

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper defaultMapper() {
        return new ModelMapper();
    }

    @Bean
    public ModelMapper categoryMapper() {
        ModelMapper mapper = new ModelMapper();

        // Handle Mismatches -> Para manejar aquellos que no se pueden reconocer o se necesite reconocer diferente
        // Lectura
        mapper.createTypeMap(Category.class, CategoryDTO.class)
                .addMapping(Category::getName, (dest, v) -> dest.setNameOfCategory((String) v));

        // Escritura
        mapper.createTypeMap(CategoryDTO.class, Category.class)
                .addMapping(CategoryDTO::getNameOfCategory, (dest, v) -> dest.setName((String) v));

        return mapper;
    }
}
