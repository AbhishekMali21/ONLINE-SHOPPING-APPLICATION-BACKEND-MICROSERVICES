package com.shopping.orderservice.request;

import java.math.BigDecimal;

public record OrderRequest(String skuCode, BigDecimal price, Integer quantity) {
}
