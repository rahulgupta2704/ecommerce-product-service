package dev.rahul.productservice.repositories;

import dev.rahul.productservice.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository
extends JpaRepository<Category, UUID> {
    //DOUBT: unable to write this query
    //Category findByIdEquals(UUID uuid);
    @Query("SELECT DISTINCT a.name FROM Category a")
    List<String> findDistinctCategories();
}
