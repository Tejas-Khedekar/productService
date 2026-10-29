package dev.tejas.productservice.repositories;

import dev.tejas.productservice.models.Category;
import dev.tejas.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByTitle(String title);
    Category save(Category category);

}
