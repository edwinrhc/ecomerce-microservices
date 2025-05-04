package com.edwinrhc.orderservice.feign;

import com.edwinrhc.orderservice.dto.product.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="product-service")
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long id);
}
