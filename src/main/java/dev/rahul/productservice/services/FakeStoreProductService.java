package dev.rahul.productservice.services;

import dev.rahul.productservice.dtos.FakeStoreProductDto;
import dev.rahul.productservice.dtos.GenericProductDto;
import dev.rahul.productservice.models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

@Service
public class FakeStoreProductService implements ProductService {

    private String specificProductsUrl = "https://fakestoreapi.com/products/{id}";

    private RestTemplateBuilder restTemplateBuilder;

    //JAVA doubt: what's happening here? how are we calling the FakeStoreProductService class inside the FakeStoreProductService class itself and how are we passing the rest template builder inside it
    //if it is a constructor that will need a RestTemplateBuilder parameter when we call the productService then why didn't we pass it in the getProductById method in the controller
    public FakeStoreProductService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }
    public GenericProductDto getProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        //Doubt what's <> this in JAVA?
        ResponseEntity<FakeStoreProductDto> response =
                restTemplate.getForEntity(specificProductsUrl, FakeStoreProductDto.class, id);
        //Takes in 3 parameters requestUrl, responseClass and the variables in any in the url in order, eg: id

        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        //stores the response in the fakeStoreProdDto Variable

//        Product product = new Product();
//        product.setImage(fakeStoreProductDto.getImage());
//        product.setDescription(fakeStoreProductDto.getDescription());
//        product.setTitle(fakeStoreProductDto.getTitle());
        // ***THis was giving an error because fakeStoreProductDto category is a string but in the Product it is of type Category
//        product.setCategory(fakeStoreProductDto.getCategory());

        GenericProductDto product = new GenericProductDto();
        product.setImage(fakeStoreProductDto.getImage());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setCategory(fakeStoreProductDto.getCategory());
        product.setPrice(fakeStoreProductDto.getPrice());
        //return new Product();
        return product;
    }

    private String productRequestsBaseUrl = "https://fakestoreapi.com/products";
    public GenericProductDto createProduct(GenericProductDto product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        //ResponseEntity<GenericProductDto> response: Here we are directly using the GenericProductDto because:
        //It is we who are creating the request and not getting it form any third party
        //If we were to actually sent it to a third party API then we would have created the GenericProduct and then converted it to the FakestoreDto and then sent it to the fakestore server
        //Basically the opposite of getProductById
        ResponseEntity<GenericProductDto> response = restTemplate.postForEntity(
                productRequestsBaseUrl, product, GenericProductDto.class);

        //How come response.getBody is getting returned? The return type of createProduct is GenericProductDto
        return response.getBody();
    }

    public GenericProductDto deleteProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange(
                specificProductsUrl,HttpMethod.DELETE, null, FakeStoreProductDto.class, id);

        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        GenericProductDto product = new GenericProductDto();
        product.setImage(fakeStoreProductDto.getImage());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setCategory(fakeStoreProductDto.getCategory());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setId(fakeStoreProductDto.getId());
        //return new Product();
        return product;
    }


}
