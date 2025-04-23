package com.edwinrhc.productservice.restImpl;

import com.edwinrhc.productservice.constants.ProductConstants;
import com.edwinrhc.productservice.dto.ProductDTO;
import com.edwinrhc.productservice.rest.ProductRest;
import com.edwinrhc.productservice.service.ProductService;
import com.edwinrhc.productservice.utils.ProductUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestImpl implements ProductRest {

    @Autowired
    ProductService productService;

    @Override
    public ResponseEntity<String> createProduct(ProductDTO productDTO) {
        try{
            return productService.createProduct(productDTO);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ProductUtils.getResponseEntity(ProductConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
