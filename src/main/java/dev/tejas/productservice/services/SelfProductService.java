package dev.tejas.productservice.services;

import dev.tejas.productservice.models.Category;
import dev.tejas.productservice.models.Product;
import dev.tejas.productservice.repositories.CategoryRepository;
import dev.tejas.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("selfProductService")
public class SelfProductService implements ProductService{

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    @Override
    public Product getSingleProduct(Long productId) {
        return null;
    }

    @Override
    public List<Product> getProducts() {
        return List.of();
    }

    @Override
    public Product createProduct(String title, String description, String category, double price, String image) {
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(image);

        Category categoryFromDatabase  = categoryRepository.findByTitle(category);

        if(categoryFromDatabase  == null){
//            category1 = new Category();
//            category1.setTitle(title);
            Category newCategory = new Category();
            newCategory.setTitle(category);
            categoryFromDatabase = categoryRepository.save(newCategory);
        }

        product.setCategory(categoryFromDatabase);

        Product savedProduct = productRepository.save(product);
        return savedProduct;


    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public List<String> getAllCategories() {
        return List.of();
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {

    }

    @Override
    public List<Product> getProductsInCategory(String categoryName) {
        return List.of();
    }
}
