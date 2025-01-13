package com.shopping.productservice.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String cId) {
        super(String.format("Category with cId: %s is not available", cId));
    }

}
