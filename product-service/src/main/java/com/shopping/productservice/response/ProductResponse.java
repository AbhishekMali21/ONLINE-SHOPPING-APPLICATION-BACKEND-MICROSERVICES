package com.shopping.productservice.response;

import java.math.BigDecimal;

public record ProductResponse(String pId, String pName, String pDescription, BigDecimal pPrice) {
}
