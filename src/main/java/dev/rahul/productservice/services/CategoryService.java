package dev.rahul.productservice.services;

import dev.rahul.productservice.dtos.GenericProductDto;
import dev.rahul.productservice.models.Category;

import java.util.List;

public interface CategoryService {
    List<String> getAllCategories();
    List<GenericProductDto> getProductsByCategory(String id);
}
