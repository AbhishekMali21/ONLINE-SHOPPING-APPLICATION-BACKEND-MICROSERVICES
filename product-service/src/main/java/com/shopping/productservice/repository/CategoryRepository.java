package com.shopping.productservice.repository;

import com.shopping.productservice.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
    Page<Category> findAll(Pageable pageable);
}
