package dev.rahul.productservice.services;

import dev.rahul.productservice.dtos.GenericProductDto;
import dev.rahul.productservice.repositories.CategoryRepository;
import dev.rahul.productservice.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class SelfCategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;

    public SelfCategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<String> getAllCategories() {

        return categoryRepository.findDistinctCategories();
    }

    public List<GenericProductDto> getProductsByCategory(String id) {
        return null;
    }
}
