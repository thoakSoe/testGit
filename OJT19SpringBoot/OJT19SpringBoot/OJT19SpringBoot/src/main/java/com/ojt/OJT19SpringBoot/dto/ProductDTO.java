package com.ojt.OJT19SpringBoot.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
    private String category;
    /*private String imageUrl;*/
    private List<String> imageUrls;
}
