package com.shopping.productservice.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String pId) {
        super(String.format("Product with pId: %s is not available", pId));
    }
}
