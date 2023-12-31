package dev.rahul.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GenericProductDto {
    private String id;
    private String title;
    private Double price;
    private String category;
    private String description;
    private String image;
}
