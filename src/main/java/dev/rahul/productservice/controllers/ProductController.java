package dev.rahul.productservice.controllers;

import dev.rahul.productservice.dtos.ExceptionDto;
import dev.rahul.productservice.dtos.GenericProductDto;
import dev.rahul.productservice.exceptions.NotFoundException;
import dev.rahul.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products") //
public class ProductController {
    //    @Autowired
    // field injection
    private ProductService productService;
    // ....;
    // ...;



    // constructor injection
    //    @Autowired // No need to autowire, spring itself recognizes that this is a special class and autowires it to the product service
    public ProductController(ProductService productService) {
            this.productService = productService;
        }
    //

    // setter injection
    //    @Autowired
    //    public void setProductService(ProductService productService) {
    //        this.productService = productService;
    //    }


    @GetMapping //Annotation to let spring know this is a Get request
    public List<GenericProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("{id}") //end point and the variable if any
    public GenericProductDto getProductById(@PathVariable("id") Long id) throws NotFoundException {
            return productService.getProductById(id);
    }


    //Doubt: If we are gonna implement this controller method to return a genericProductDto object why are we passing the genericProductDto object to it?
    //why can't we just directly can the service and the service will return the GenericProductDto
    @PostMapping
    public GenericProductDto createProduct(@RequestBody GenericProductDto product) {
        //(@RequestBody GenericProductDto product): in a post request we need to send the body
        // In the get product by id request we were converting the json in the response to genericProductDto class
        //But in the create product we are converting the json body that we need to send to the GenericProductDto and sending the request
        //Refer the Postman Screenshot
        return productService.createProduct(product);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<GenericProductDto> deleteProductById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                productService.deleteProductById(id),
                HttpStatus.OK
        );
    }

    @PutMapping("{id}")
    public GenericProductDto updateProductById(@PathVariable("id") Long id, @RequestBody GenericProductDto product) {
        return productService.updateProductById(id, product);
    }
}
