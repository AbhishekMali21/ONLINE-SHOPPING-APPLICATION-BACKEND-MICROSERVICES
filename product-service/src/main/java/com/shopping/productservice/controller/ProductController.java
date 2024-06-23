package com.shopping.productservice.controller;

import com.shopping.productservice.request.ProductRequest;
import com.shopping.productservice.response.ProductResponse;
import com.shopping.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{pId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProductByPId(@PathVariable("pId") String pId) {
        return productService.getProductByPId(pId);
    }

    @GetMapping("/getAllProducts")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }
}
