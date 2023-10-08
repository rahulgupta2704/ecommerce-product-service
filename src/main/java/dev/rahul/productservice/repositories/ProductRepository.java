package dev.rahul.productservice.repositories;

import dev.rahul.productservice.models.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public interface ProductRepository
extends JpaRepository<Product, UUID> {


    Product findByIdEquals(UUID uuid);
}
