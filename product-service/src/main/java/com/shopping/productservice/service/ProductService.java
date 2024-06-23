package com.shopping.productservice.service;

import com.shopping.productservice.entities.Product;
import com.shopping.productservice.exception.ProductNotFoundException;
import com.shopping.productservice.repository.ProductRepository;
import com.shopping.productservice.request.ProductRequest;
import com.shopping.productservice.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse getProductByPId(String pId) {
        return productRepository.findById(pId)
                .map(product -> new ProductResponse(product.getPId(), product.getPName(), product.getPDescription(), product.getPPrice()))
                .orElseThrow(() -> new ProductNotFoundException("Product with pId " + pId + " is not available"));
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(product.getPId(), product.getPName(), product.getPDescription(), product.getPPrice()))
                .toList();
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .pName(productRequest.pName())
                .pDescription(productRequest.pDescription())
                .pPrice(productRequest.pPrice())
                .build();
        productRepository.save(product);
        log.info("product created with product pId is: {}", product.getPId());
        return new ProductResponse(product.getPId(), product.getPName(), product.getPDescription(), product.getPPrice());
    }

    public ProductResponse updateProduct(String pId, ProductRequest productRequest) {
        Product product = productRepository.findById(pId)
                .orElseThrow(() -> new ProductNotFoundException("Product with pId " + pId + " is not available"));
        product.setPName(productRequest.pName());
        product.setPDescription(productRequest.pDescription());
        product.setPPrice(productRequest.pPrice());
        productRepository.save(product);
        return new ProductResponse(product.getPId(), product.getPName(), product.getPDescription(), product.getPPrice());
    }

    public String deleteProduct(String pId) {
        productRepository.deleteById(pId);
        return "product deleted with product pId: " + pId;
    }

    public List<ProductResponse> findByNameContaining(String pName) {
        return productRepository.findByNameContaining(pName)
                .stream()
                .map(product -> new ProductResponse(product.getPId(), product.getPName(),product.getPDescription(), product.getPPrice()))
                .toList();
    }
}
