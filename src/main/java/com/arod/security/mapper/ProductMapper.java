package com.arod.security.mapper;

import com.arod.security.dto.request.ProductRequestDTO;
import com.arod.security.dto.response.ProductResponseDTO;
import com.arod.security.persistence.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "productName", target = "name")
    @Mapping(source = "productPrice", target = "price")
    @Mapping(source = "productValue", target = "value")
    Product toEntity(ProductRequestDTO request);

    @Mapping(source = "id", target = "productID")
    @Mapping(source = "name", target = "productName")
    @Mapping(source = "price", target = "productPrice")
    @Mapping(source = "value", target = "productValue")
    ProductResponseDTO toResponse(Product product);

    @Mapping(source = "productName", target = "name")
    @Mapping(source = "productPrice", target = "price")
    @Mapping(source = "productValue", target = "value")
    void updateValues(ProductRequestDTO request, @MappingTarget Product product);
}
