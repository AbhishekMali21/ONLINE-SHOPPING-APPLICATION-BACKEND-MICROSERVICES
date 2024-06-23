package com.shopping.productservice.repository;

import com.shopping.productservice.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    @Query("{ 'pName' : { $regex: ?0, $options: 'i' } }")
    List<Product> findByNameContaining(String pName);

    @Query("{'pPrice' : { $gte: ?0, $lte: ?1} }")
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}
