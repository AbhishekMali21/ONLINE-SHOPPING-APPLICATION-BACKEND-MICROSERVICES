package com.shopping.productservice.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank(message = "Name is mandatory") String cName,
        @NotBlank(message = "Description is mandatory") String cDescription
) {
}
