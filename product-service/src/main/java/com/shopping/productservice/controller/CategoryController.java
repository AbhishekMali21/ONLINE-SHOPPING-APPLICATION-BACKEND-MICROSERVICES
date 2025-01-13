package com.shopping.productservice.controller;

import com.shopping.productservice.request.CategoryRequest;
import com.shopping.productservice.response.CategoryResponse;
import com.shopping.productservice.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{cId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get category by cId", description = "Fetch a category by its cId.")
    public CategoryResponse getCategory(@PathVariable("cId") String cId) {
        return categoryService.getCategory(cId);
    }

    @GetMapping("/getAllCategories")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get paginated categories", description = "Fetch categories with pagination and sorting.")
    @Parameters({
            @Parameter(name = "page", description = "Page number", example = "0"),
            @Parameter(name = "size", description = "Number of items per page", example = "1"),
            @Parameter(name = "sort", description = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.", example = "cName,asc")
    })
    public List<CategoryResponse> getAllCategories(Pageable pageable) {
        return categoryService.getAllCategories(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a category", description = "Create a new category.")
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }

    @DeleteMapping("/{cId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete category by cId", description = "Delete a category by its cId.")
    public String deleteCategory(@PathVariable("cId") String cId) {
        return categoryService.deleteCategory(cId);
    }
}
