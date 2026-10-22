package dev.tejas.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequestDTO {

    private Long id;
    private String title;
    private String image;
    private String description;
    private String category;
    private double price;
    private Long userId;

}
