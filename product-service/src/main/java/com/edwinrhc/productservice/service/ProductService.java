package com.edwinrhc.productservice.service;

import com.edwinrhc.productservice.dto.product.CreateProductDTO;
import com.edwinrhc.productservice.dto.product.UpdateProductDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    ResponseEntity<String> createProduct(CreateProductDTO createProductDTO);
    ResponseEntity<String> updateProduct(UpdateProductDTO updateProductDTO);

    List<CreateProductDTO> getAllProducts();
    CreateProductDTO getProductById(Long id);



}
