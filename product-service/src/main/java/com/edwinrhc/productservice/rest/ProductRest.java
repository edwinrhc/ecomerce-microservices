package com.edwinrhc.productservice.rest;

import com.edwinrhc.productservice.dto.product.CreateProductDTO;
import com.edwinrhc.productservice.dto.product.UpdateProductDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/products")
public interface ProductRest {

    @PostMapping(value="/add")
    ResponseEntity<String> createProduct(@RequestBody @Valid CreateProductDTO createProductDTO);

    @PutMapping(value ="/update")
    ResponseEntity<String> updateProduct(@RequestBody @Valid UpdateProductDTO updateProductDTO);

    @GetMapping("/get")
    ResponseEntity<List<CreateProductDTO>> getAllProducts();

    @GetMapping(value="/name/{name}")
    ResponseEntity<List<CreateProductDTO>> getProductsByName(@PathVariable String name);

    @GetMapping(value="/category/{category}")
    ResponseEntity<List<CreateProductDTO>> getProductsByCategory(@PathVariable String category);

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable Long id);

    @GetMapping("/{id}")
    ResponseEntity<CreateProductDTO> getProductById(@PathVariable Long id);

}
