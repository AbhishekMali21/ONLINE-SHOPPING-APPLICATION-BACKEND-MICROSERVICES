package com.shopping.productservice.service;

import com.shopping.productservice.entities.Category;
import com.shopping.productservice.exception.CategoryNotFoundException;
import com.shopping.productservice.repository.CategoryRepository;
import com.shopping.productservice.request.CategoryRequest;
import com.shopping.productservice.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse getCategory(String cId) {
        return categoryRepository.findById(cId)
                .map(category -> new CategoryResponse(category.getCId(), category.getCName(), category.getCDescription()))
                .orElseThrow(() -> new CategoryNotFoundException(cId));
    }

    public List<CategoryResponse> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .stream()
                .map(category -> new CategoryResponse(category.getCId(), category.getCName(), category.getCDescription()))
                .toList();
    }

    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = Category.builder()
                .cName(categoryRequest.cName())
                .cDescription(categoryRequest.cDescription())
                .build();
        categoryRepository.save(category);
        log.info("category created with category cId is: {}", category.getCId());
        return new CategoryResponse(category.getCId(), category.getCName(), category.getCDescription());
    }

    public String deleteCategory(String cId) {
        categoryRepository.deleteById(cId);
        return "product deleted with product pId: " + cId;
    }
}
