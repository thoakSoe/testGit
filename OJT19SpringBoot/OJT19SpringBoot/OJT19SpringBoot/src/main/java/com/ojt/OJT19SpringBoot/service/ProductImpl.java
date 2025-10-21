package com.ojt.OJT19SpringBoot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ojt.OJT19SpringBoot.entity.ProductImageEntity;
import com.ojt.OJT19SpringBoot.repository.ProductImageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojt.OJT19SpringBoot.dto.ProductDTO;
import com.ojt.OJT19SpringBoot.entity.ProductEntity;
import com.ojt.OJT19SpringBoot.exception.ProductNotFoundException;
import com.ojt.OJT19SpringBoot.repository.ProductRepository;

@Service
public class ProductImpl implements ProductService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ProductImageRepository productImageRepo;

    @Autowired
    private ModelMapper mapper;

    /*@Override
    public ProductDTO saveProduct(ProductDTO product) {
        ProductEntity entity = mapper.map(product, ProductEntity.class);
        ProductEntity saved = productRepo.save(entity);
        return mapper.map(saved, ProductDTO.class);
    }*/

    @Override
    public ProductDTO saveProduct(ProductDTO dto) {
        ProductEntity entity = new ProductEntity();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setCategory(dto.getCategory());

        // Convert image URLs to entities
        List<ProductImageEntity> imageEntities = dto.getImageUrls().stream().map(url -> {
            ProductImageEntity img = new ProductImageEntity();
            img.setImageUrl(url);
            img.setProduct(entity);
            return img;
        }).collect(Collectors.toList());

        entity.setImages(imageEntities);

        ProductEntity saved = productRepo.save(entity);

        ProductDTO result = new ProductDTO();
        result.setId(saved.getId());
        result.setName(saved.getName());
        result.setPrice(saved.getPrice());
        result.setCategory(saved.getCategory());
        result.setImageUrls(saved.getImages().stream()
                .map(ProductImageEntity::getImageUrl)
                .collect(Collectors.toList()));

        return result;
    }


   /* @Override
    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> entityList = productRepo.findAll();
        return entityList.stream()
                .map(entity -> mapper.map(entity, ProductDTO.class))
                .collect(Collectors.toList());
    }*/

    @Override
    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> entities = productRepo.findAll();
        List<ProductDTO> dtos = new ArrayList<>();

        for (ProductEntity entity : entities) {
            ProductDTO dto = new ProductDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setPrice(entity.getPrice());
            dto.setCategory(entity.getCategory());

            // fetch images from ProductImage table
            List<ProductImageEntity> images = productImageRepo.findByProductId(entity.getId());
            List<String> urls = images.stream()
                    .map(ProductImageEntity::getImageUrl)
                    .collect(Collectors.toList());
            dto.setImageUrls(urls);

            dtos.add(dto);
        }
        return dtos;
    }


    @Override
    public ProductDTO getById(Long id) {
        ProductEntity entity = productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
        return mapper.map(entity, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO product) {
        Optional<ProductEntity> res = productRepo.findById(product.getId());
        ProductEntity entity = res.orElseThrow(() -> new ProductNotFoundException("Product not found!"));

        entity.setName(product.getName());
        entity.setPrice(product.getPrice());
        entity.setCategory(product.getCategory());
        //entity.setImageUrl(product.getImageUrl());

        ProductEntity updated = productRepo.save(entity);
        return mapper.map(updated, ProductDTO.class);
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<ProductEntity> res = productRepo.findById(id);
        ProductEntity entity = res.orElseThrow(() -> new ProductNotFoundException("Product not found!"));
        productRepo.delete(entity);
    }

    @Override
    public List<ProductDTO> findByCategory(String category) {
        List<ProductEntity> entityList = productRepo.findByCategory(category);
        return entityList.stream()
                .map(entity -> mapper.map(entity, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> findByPriceGreaterThan(Double price) {
        List<ProductEntity> entityList = productRepo.findByPriceGreaterThan(price);
        return entityList.stream()
                .map(entity -> mapper.map(entity, ProductDTO.class))
                .collect(Collectors.toList());
    }
}
