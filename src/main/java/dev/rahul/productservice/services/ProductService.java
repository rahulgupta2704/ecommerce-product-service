package dev.rahul.productservice.services;

import dev.rahul.productservice.dtos.GenericProductDto;
import dev.rahul.productservice.exceptions.NotFoundException;
import dev.rahul.productservice.models.Product;

import java.util.List;

public interface ProductService {
    GenericProductDto getProductById(String id) throws NotFoundException;

    GenericProductDto createProduct(GenericProductDto genericProductDto);

    GenericProductDto deleteProductById(String id);

    List<GenericProductDto> getAllProducts();

    GenericProductDto updateProductById(Long id, GenericProductDto product);
}
