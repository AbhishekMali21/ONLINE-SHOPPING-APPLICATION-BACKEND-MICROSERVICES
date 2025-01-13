package com.shopping.productservice.service;

import com.shopping.productservice.entities.Category;
import com.shopping.productservice.entities.Product;
import com.shopping.productservice.exception.CategoryNotFoundException;
import com.shopping.productservice.exception.ProductNotFoundException;
import com.shopping.productservice.repository.CategoryRepository;
import com.shopping.productservice.repository.ProductRepository;
import com.shopping.productservice.request.ProductRequest;
import com.shopping.productservice.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponse getProductByPId(String pId) {
        return productRepository.findById(pId)
                .map(product -> new ProductResponse(product.getPId(), product.getPName(), product.getPDescription(), product.getPPrice(), product.getCategory().getCId()))
                .orElseThrow(() -> new ProductNotFoundException(pId));
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(product.getPId(), product.getPName(), product.getPDescription(), product.getPPrice(), product.getCategory().getCId()))
                .toList();
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.cId())
                .orElseThrow(() -> new CategoryNotFoundException(productRequest.cId()));

        Product product = Product.builder()
                .pName(productRequest.pName())
                .pDescription(productRequest.pDescription())
                .pPrice(productRequest.pPrice())
                .category(category)
                .build();
        productRepository.save(product);
        log.info("product created with product pId is: {}", product.getPId());
        return new ProductResponse(product.getPId(), product.getPName(), product.getPDescription(), product.getPPrice(), product.getCategory().getCId());
    }

    public ProductResponse updateProduct(String pId, ProductRequest productRequest) {
        Product product = productRepository.findById(pId)
                .orElseThrow(() -> new ProductNotFoundException(pId));
        Category category = categoryRepository.findById(productRequest.cId())
                .orElseThrow(() -> new CategoryNotFoundException(productRequest.cId()));
        product.setPName(productRequest.pName());
        product.setPDescription(productRequest.pDescription());
        product.setPPrice(productRequest.pPrice());
        product.setCategory(category);
        productRepository.save(product);
        return new ProductResponse(product.getPId(), product.getPName(), product.getPDescription(), product.getPPrice(), product.getCategory().getCId());
    }

    public String deleteProduct(String pId) {
        productRepository.deleteById(pId);
        return "product deleted with product pId: " + pId;
    }

    public List<ProductResponse> findByNameContaining(String pName) {
        return productRepository.findByNameContaining(pName)
                .stream()
                .map(product -> new ProductResponse(product.getPId(), product.getPName(),product.getPDescription(), product.getPPrice(), product.getCategory().getCId()))
                .toList();
    }

    public List<ProductResponse> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice)
                .stream()
                .map(product -> new ProductResponse(product.getPId(), product.getPName(), product.getPDescription(), product.getPPrice(), product.getCategory().getCId()))
                .toList();
    }
}
