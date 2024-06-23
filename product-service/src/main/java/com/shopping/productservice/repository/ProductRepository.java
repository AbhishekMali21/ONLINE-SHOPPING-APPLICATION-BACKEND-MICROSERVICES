package com.shopping.productservice.repository;

import com.shopping.productservice.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    @Query("{ 'pName' : { $regex: ?0, $options: 'i' } }")
    List<Product> findByNameContaining(String pName);
}
