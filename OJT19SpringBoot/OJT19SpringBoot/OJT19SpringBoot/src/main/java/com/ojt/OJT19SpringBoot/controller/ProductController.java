package com.ojt.OJT19SpringBoot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ojt.OJT19SpringBoot.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ojt.OJT19SpringBoot.dto.ProductDTO;
import com.ojt.OJT19SpringBoot.service.ProductService;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products")
/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CloudinaryService cloudinaryService;

    /*@PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveProduct(@Valid @RequestBody ProductDTO dto) {
        ProductDTO saved = productService.saveProduct(dto);
        Map<String, Object> res = new HashMap<>();
        res.put("message", "Product saved successfully");
        res.put("data", saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }*/

    /*@PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveProduct(
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("category") String category,
            @RequestParam("image") MultipartFile file) throws IOException {
        String imageUrl = cloudinaryService.uploadFile(file);
        ProductDTO dto = new ProductDTO();
        dto.setName(name);
        dto.setPrice(price);
        dto.setCategory(category);
        dto.setImageUrl(imageUrl);
        ProductDTO saved = productService.saveProduct(dto);
        Map<String, Object> res = new HashMap<>();
        res.put("message", "Product saved successfully");
        res.put("data", saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }*/

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveProduct(
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("category") String category,
            @RequestParam("images") List<MultipartFile> files) throws IOException {

        ProductDTO dto = new ProductDTO();
        dto.setName(name);
        dto.setPrice(price);
        dto.setCategory(category);

        // upload each image to Cloudinary
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String imageUrl = cloudinaryService.uploadFile(file);
            imageUrls.add(imageUrl);
        }

        dto.setImageUrls(imageUrls);
        ProductDTO saved = productService.saveProduct(dto);

        Map<String, Object> res = new HashMap<>();
        res.put("message", "Product saved successfully");
        res.put("data", saved);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }




    @GetMapping("/list")
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PutMapping("/update")
    public ProductDTO updateProduct(@RequestBody ProductDTO dto) {
        return productService.updateProduct(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/category")
    public List<ProductDTO> findByCategory(@RequestParam String category){
        return productService.findByCategory(category);
    }

    @GetMapping("/price")
    public List<ProductDTO> findByPriceGreaterThan(@RequestParam Double price){
        return productService.findByPriceGreaterThan(price);
    }
}
