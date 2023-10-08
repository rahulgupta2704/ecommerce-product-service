package dev.rahul.productservice.dtos;

import dev.rahul.productservice.models.Price;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private String title;

    private String description;

    private String image;

    private Price price;
}