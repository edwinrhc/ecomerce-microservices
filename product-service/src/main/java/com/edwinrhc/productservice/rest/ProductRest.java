package com.edwinrhc.productservice.rest;

import com.edwinrhc.productservice.dto.product.CreateProductDTO;
import com.edwinrhc.productservice.dto.product.UpdateProductDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/products")
public interface ProductRest {

    @PostMapping(value="/add")
    public ResponseEntity<String> createProduct(@RequestBody @Valid CreateProductDTO createProductDTO);

    @PutMapping(value ="/update")
    public ResponseEntity<String> updateProduct(@RequestBody @Valid UpdateProductDTO updateProductDTO);

}
