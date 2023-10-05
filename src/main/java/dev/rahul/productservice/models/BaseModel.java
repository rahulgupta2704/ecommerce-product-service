package dev.rahul.productservice.models;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.UUID;

@MappedSuperclass
public class BaseModel {
    @Id
    private Long id;
}
