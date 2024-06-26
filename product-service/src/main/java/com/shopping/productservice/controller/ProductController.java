package com.shopping.productservice.controller;

import com.shopping.productservice.request.ProductRequest;
import com.shopping.productservice.response.ProductResponse;
import com.shopping.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @PutMapping("/{pId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse updateProduct(@PathVariable String pId, @Valid @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(pId, productRequest);
    }

    @DeleteMapping("/{pId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteProduct(@PathVariable("pId") String pId) {
        return productService.deleteProduct(pId);
    }

    @GetMapping("/findByNameContaining/{pName}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> findByNameContaining(@PathVariable("pName") String pName) {
        return productService.findByNameContaining(pName);
    }

    @GetMapping("/findByPriceBetween")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> findByPriceBetween(@RequestParam BigDecimal minPrice, @RequestParam BigDecimal maxPrice) {
        return productService.findByPriceBetween(minPrice, maxPrice);
    }
}
