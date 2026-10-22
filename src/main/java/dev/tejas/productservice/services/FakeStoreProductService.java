package dev.tejas.productservice.services;

import dev.tejas.productservice.dtos.FakeStoreProductDTO;
import dev.tejas.productservice.models.Product;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long productId){
        FakeStoreProductDTO fakeStoreProductDTO = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreProductDTO.class
        );
        return fakeStoreProductDTO == null ? new Product() : fakeStoreProductDTO.toProduct();
    }

    @Override
    public List<Product> getProducts() {
        return List.of();
    }

    @Override
    public Product createProduct(String title,
                                 String description,
                                 String category,
                                 double price,
                                 String image){

        FakeStoreProductDTO fakeStoreProductDTO = new FakeStoreProductDTO();
        fakeStoreProductDTO.setTitle(title);
        fakeStoreProductDTO.setCategory(category);
        fakeStoreProductDTO.setPrice(price);
        fakeStoreProductDTO.setImage(image);
        fakeStoreProductDTO.setDescription(description);

        FakeStoreProductDTO response = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                fakeStoreProductDTO,
                FakeStoreProductDTO.class
        );
        if (response == null) return new Product();

        return response.toProduct();
    }

    // --- 1. Get All Products ---
    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDTO[] response = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreProductDTO[].class
        );

        List<Product> products = new ArrayList<>();
        if (response != null) {
            for (FakeStoreProductDTO dto : response) {
                products.add(dto.toProduct());
            }
        }
        return products;
    }

    // --- 2. Get All Categories ---
    @Override
    public List<String> getAllCategories() {
        String[] response = restTemplate.getForObject(
                "https://fakestoreapi.com/products/categories",
                String[].class
        );

        return response != null ? List.of(response) : new ArrayList<>();
    }

    // --- 3. Update Product (PUT / PATCH) ---
    @Override
    public Product updateProduct(Long productId, Product product) {
        // Mapping your Product model to FakeStore DTO for updating
        FakeStoreProductDTO fakeStoreProductDTO = new FakeStoreProductDTO();
        fakeStoreProductDTO.setTitle(product.getTitle());
        fakeStoreProductDTO.setDescription(product.getDescription());
        fakeStoreProductDTO.setPrice(product.getPrice());
        fakeStoreProductDTO.setImage(product.getImageUrl());
        if (product.getCategory() != null) {
            fakeStoreProductDTO.setCategory(product.getCategory().getTitle());
        }

        // Using exchange to support PATCH/PUT requests with RestTemplate
        FakeStoreProductDTO response = restTemplate.patchForObject(
                "https://fakestoreapi.com/products/" + productId,
                fakeStoreProductDTO,
                FakeStoreProductDTO.class
        );

        return response == null ? new Product() : response.toProduct();
    }

    // --- 4. Delete a Product ---
    @Override
    public void deleteProduct(Long productId) {
        restTemplate.delete("https://fakestoreapi.com/products/" + productId);
    }

    // --- 5. Get Products in Specific Category ---
    @Override
    public List<Product> getProductsInCategory(String categoryName) {
        FakeStoreProductDTO[] response = restTemplate.getForObject(
                "https://fakestoreapi.com/products/category/" + categoryName,
                FakeStoreProductDTO[].class
        );

        List<Product> products = new ArrayList<>();
        if (response != null) {
            for (FakeStoreProductDTO dto : response) {
                products.add(dto.toProduct());
            }
        }
        return products;
    }
}