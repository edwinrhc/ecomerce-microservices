package com.edwinrhc.productservice.rest;

import com.edwinrhc.productservice.dto.ProductDTO;
import com.edwinrhc.productservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/products")
public interface ProductRest {

    @PostMapping(value="/add")
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO);

}
