package dev.tejas.productservice.controllers;

import dev.tejas.productservice.dtos.CreateProductRequestDTO;
import dev.tejas.productservice.exceptions.ProductNotFoundException;
import dev.tejas.productservice.models.Product;
import dev.tejas.productservice.services.ProductService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;
    private final RestTemplate restTemplate;

    public ProductController(ProductService productService, RestTemplate restTemplate) {
        this.productService = productService;
        this.restTemplate = restTemplate;
    }

    // 1. Create Product
    @PostMapping("/products")
    public Product createProduct(@RequestBody CreateProductRequestDTO request) throws ProductNotFoundException {
        if (request.getTitle() == null || request.getTitle().isEmpty()) {
            throw new ProductNotFoundException("Product title cannot be empty.");
        }
        return productService.createProduct(
                request.getTitle(),
                request.getDescription(),
                request.getCategory(),
                request.getPrice(),
                request.getImage()
        );
    }

    // 2. Get Single Product Details
    @GetMapping("/products/{id}")
    public Product getProductDetails(@PathVariable("id") Long productId) throws ProductNotFoundException {
        Product product = productService.getSingleProduct(productId);
        if (product == null || product.getTitle() == null) {
            throw new ProductNotFoundException("Product with id " + productId + " does not exist.");
        }
        return product;
    }

    // 3. Get All Products
    @GetMapping("/products")
    public List<Product> getAllProducts() throws ProductNotFoundException {
        List<Product> products = productService.getAllProducts();
        if (products == null || products.isEmpty()) {
            throw new ProductNotFoundException("No products found.");
        }
        return products;
    }

    // 4. Get All Categories
    @GetMapping("/products/categories")
    public List<String> getAllCategories() throws ProductNotFoundException {
        List<String> categories = productService.getAllCategories();
        if (categories == null || categories.isEmpty()) {
            throw new ProductNotFoundException("No categories found.");
        }
        return categories;
    }

    // 5. Update Product
    @PatchMapping("/products/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product) throws ProductNotFoundException {
        Product existingProduct = productService.getSingleProduct(id);
        if (existingProduct == null) {
            throw new ProductNotFoundException("Cannot update. Product with id " + id + " does not exist.");
        }
        return productService.updateProduct(id, product);
    }

    // 6. Delete a Product
    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        Product product = productService.getSingleProduct(id);
        if (product == null) {
            throw new ProductNotFoundException("Cannot delete. Product with id " + id + " does not exist.");
        }
        productService.deleteProduct(id);
    }

    // 7. Get Products in Specific Category
    @GetMapping("/products/category/{categoryName}")
    public List<Product> getProductsInCategory(@PathVariable("categoryName") String categoryName) throws ProductNotFoundException {
        List<Product> products = productService.getProductsInCategory(categoryName);
        if (products == null || products.isEmpty()) {
            throw new ProductNotFoundException("No products found under category: " + categoryName);
        }
        return products;
    }
}