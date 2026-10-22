package dev.tejas.productservice;

import dev.tejas.productservice.controllers.ProductController;
import dev.tejas.productservice.services.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductServiceApplication {

    private ProductService productService;
    private ProductController productController;

    public ProductServiceApplication(ProductController productController, ProductService productService){
        this.productService = productService;
        this.productController = productController;
    }



    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

}
