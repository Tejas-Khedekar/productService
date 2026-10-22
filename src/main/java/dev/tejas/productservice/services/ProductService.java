package dev.tejas.productservice.services;

import dev.tejas.productservice.models.Product;
import java.util.List;

public interface ProductService {

    Product getSingleProduct(Long productId);

    List<Product> getProducts();

    Product createProduct(String title,
                          String description,
                          String category,
                          double price,
                          String image);

    // --- New Methods ---
    List<Product> getAllProducts();
    List<String> getAllCategories();
    Product updateProduct(Long productId, Product product);
    void deleteProduct(Long productId);
    List<Product> getProductsInCategory(String categoryName);
}