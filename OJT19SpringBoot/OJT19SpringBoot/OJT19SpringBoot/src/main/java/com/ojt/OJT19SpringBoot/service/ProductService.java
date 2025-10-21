package com.ojt.OJT19SpringBoot.service;

import java.util.List;

import com.ojt.OJT19SpringBoot.dto.ProductDTO;

public interface ProductService {
    public ProductDTO saveProduct(ProductDTO product);
    public List<ProductDTO> getAllProducts();
    public ProductDTO getById(Long id);
    public ProductDTO updateProduct(ProductDTO product);
    public void deleteProduct(Long id);
    public List<ProductDTO> findByCategory(String category);
    public List<ProductDTO> findByPriceGreaterThan(Double price);
}
