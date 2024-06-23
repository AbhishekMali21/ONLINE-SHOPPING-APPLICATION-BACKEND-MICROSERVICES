package com.shopping.productservice.request;

import java.math.BigDecimal;

public record ProductRequest(String pName, String pDescription, BigDecimal pPrice) {
}
