package com.example.store_management_tool.config;

import com.example.store_management_tool.data.dtos.ProductRequestDto;
import com.example.store_management_tool.data.entities.Product;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Skip mapping of the creation date field
        modelMapper.typeMap(ProductRequestDto.class, Product.class)
                .addMappings(mapper -> mapper.skip(Product::setModifiedDate));

//        modelMapper.addMappings(new PropertyMap<ProductRequestDto, Product>() {
//            @Override
//            protected void configure() {
//                skip(destination.getCreatedDate());
//            }
//        });

        return modelMapper;
    }
}
