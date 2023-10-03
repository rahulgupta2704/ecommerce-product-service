package dev.rahul.productservice.services;

import dev.rahul.productservice.dtos.FakeStoreProductDto;
import dev.rahul.productservice.dtos.GenericProductDto;
import dev.rahul.productservice.exceptions.NotFoundException;
import dev.rahul.productservice.models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService {

    private String specificProductsUrl = "https://fakestoreapi.com/products/{id}";

    private RestTemplateBuilder restTemplateBuilder;

    private GenericProductDto convertFakeStoreProductDtoToGenericProductDto(FakeStoreProductDto fakeStoreProductDto) {
        GenericProductDto product = new GenericProductDto();
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setImage(fakeStoreProductDto.getImage());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setCategory(fakeStoreProductDto.getCategory());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setId(fakeStoreProductDto.getId());

        return product;
    }

    private FakeStoreProductDto convertGenericProductDtoToFakeStoreProductDto(GenericProductDto genericProductDto) {
        FakeStoreProductDto product = new FakeStoreProductDto();
        product.setTitle(genericProductDto.getTitle());
        product.setImage(genericProductDto.getImage());
        product.setDescription(genericProductDto.getDescription());
        product.setCategory(genericProductDto.getCategory());
        product.setPrice(genericProductDto.getPrice());
        product.setId(genericProductDto.getId());

        return product;
    }

    //JAVA doubt: what's happening here? how are we calling the FakeStoreProductService class inside the FakeStoreProductService class itself and how are we passing the rest template builder inside it
    //if it is a constructor that will need a RestTemplateBuilder parameter when we call the productService then why didn't we pass it in the getProductById method in the controller
    public FakeStoreProductService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }
    public GenericProductDto getProductById(Long id) throws NotFoundException {
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

        //If the response body is empty, the fakeStoreProductDto will be null
        //Also it's better to assert the fakeStoreProductDto in the if condition instead of the response to have a layer
        if(fakeStoreProductDto == null) {
           throw new NotFoundException("Product with id " + id + " doesn't exist");
        }

        //return new Product();
        return convertFakeStoreProductDtoToGenericProductDto(response.getBody());
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

        //Doubt: How come response.getBody is getting returned? The return type of createProduct is GenericProductDto
        return response.getBody();
    }

    public GenericProductDto deleteProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange(
                specificProductsUrl,HttpMethod.DELETE, null, FakeStoreProductDto.class, id);

        //return new Product();
        return convertFakeStoreProductDtoToGenericProductDto(response.getBody());
    }

    public List<GenericProductDto> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        //Doubt: Why are we using ArrayList/List/Array and why not only one data type
        ResponseEntity<FakeStoreProductDto[]> response =
                restTemplate.getForEntity(productRequestsBaseUrl, FakeStoreProductDto[].class);

        List<GenericProductDto> genericProductDtoList = new ArrayList<>();

        for(FakeStoreProductDto fakeStoreProductDto : response.getBody())
        {
            genericProductDtoList.add(convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductDto));
        }

        return genericProductDtoList;
    }

    public GenericProductDto updateProductById(Long id, GenericProductDto product) {
        //Converting the GenericProduct that we send as a request to fakeStoreProduct
        FakeStoreProductDto fakeStoreProduct = convertGenericProductDtoToFakeStoreProductDto(product);

        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProduct, FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(specificProductsUrl, HttpMethod.PUT, requestCallback, responseExtractor, id);

        //Converting the fakeStoreProduct that we receive as a response to GenericProduct
        return convertFakeStoreProductDtoToGenericProductDto(response.getBody());
    }
}
