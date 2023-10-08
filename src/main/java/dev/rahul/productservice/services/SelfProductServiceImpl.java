package dev.rahul.productservice.services;

import dev.rahul.productservice.dtos.FakeStoreProductDto;
import dev.rahul.productservice.dtos.GenericProductDto;
import dev.rahul.productservice.models.Category;
import dev.rahul.productservice.models.Price;
import dev.rahul.productservice.models.Product;
import dev.rahul.productservice.repositories.CategoryRepository;
import dev.rahul.productservice.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("selfProductServiceImpl")
@Primary
public class SelfProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    public SelfProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


//DOUBT: This code is giving an error
//    private CategoryRepository categoryRepository;
//
//    public SelfProductServiceImpl(CategoryRepository categoryRepository) {
//        this.categoryRepository = categoryRepository;
//    }

    private GenericProductDto convertProductToGenericProductDto(Product product) {
        GenericProductDto genericProductDto = new GenericProductDto();
        genericProductDto.setId(product.getId().toString());
        genericProductDto.setDescription(product.getDescription());
        genericProductDto.setTitle(product.getTitle());
        genericProductDto.setImage(product.getImage());

        //Category category = categoryRepository.findByProduct_Id(product.getId());

        //genericProductDto.setCategory(category.getName());
        genericProductDto.setCategory(null);
        genericProductDto.setPrice(null);
        return genericProductDto;
    }

    @Override
    public GenericProductDto getProductById(String id) {
        Product product = productRepository.findByIdEquals(UUID.fromString(id));
        return convertProductToGenericProductDto(product);
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto product) {
        //creating category object
        Category category = new Category();
        category.setName(product.getCategory());

        //creating price object
        Price price = new Price();
        price.setPrice(product.getPrice());
        price.setCurrency("Rupees");

        //Creating a product entity
        Product productEntity = new Product();
        productEntity.setTitle(product.getTitle());
        productEntity.setCategory(category);
        productEntity.setDescription(product.getDescription());
        productEntity.setPrice(price);
        productEntity.setImage(product.getImage());

        //Saving the product to DB
        productRepository.save(productEntity);

        return product;
    }

    @Override
    public List<GenericProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();

        List<GenericProductDto> genericProductDtoList = new ArrayList<>();

        for(Product product : products)
        {
            genericProductDtoList.add(convertProductToGenericProductDto(product));
        }

        return genericProductDtoList;
    }

    @Override
    public GenericProductDto deleteProductById(String id) {
        Product product = productRepository.findByIdEquals(UUID.fromString(id));

        //Doubt: Why can't we delete by ID?? How will it affect the other cardinal tables
        productRepository.delete(product);
        return convertProductToGenericProductDto(product);
    }

    @Override
    public GenericProductDto updateProductById(Long id, GenericProductDto product) {
        return null;
    }
}
