package dev.rahul.productservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Category extends BaseModel{
    private String name;

    @OneToMany(mappedBy = "category")
    // mapped by category: this is the same relation being mapped by category attribute in the other (Product) class
    private List<Product> products;
}
