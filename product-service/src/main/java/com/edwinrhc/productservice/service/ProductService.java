package com.edwinrhc.productservice.service;

import com.edwinrhc.productservice.dto.ProductDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    ResponseEntity<String> createProduct(ProductDTO productDTO);
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);

}
