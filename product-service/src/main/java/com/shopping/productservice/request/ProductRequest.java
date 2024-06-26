package com.shopping.productservice.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Name is mandatory") String pName,
        @NotBlank(message = "Description is mandatory") String pDescription,
        @Positive(message = "Price should be greater than 0") BigDecimal pPrice
) {
}
