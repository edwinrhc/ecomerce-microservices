package com.edwinrhc.productservice.restImpl;

import com.edwinrhc.productservice.constants.ProductConstants;
import com.edwinrhc.productservice.dto.product.CreateProductDTO;
import com.edwinrhc.productservice.dto.product.UpdateProductDTO;
import com.edwinrhc.productservice.rest.ProductRest;
import com.edwinrhc.productservice.service.ProductService;
import com.edwinrhc.productservice.utils.ProductUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductRestImpl implements ProductRest {

    @Autowired
    ProductService productService;

    @Override
    public ResponseEntity<String> createProduct(CreateProductDTO createProductDTO) {
        try{
            return productService.createProduct(createProductDTO);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ProductUtils.getResponseEntity(ProductConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(UpdateProductDTO updateProductDTO) {
        try{
            return productService.updateProduct(updateProductDTO);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ProductUtils.getResponseEntity(ProductConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<List<CreateProductDTO>> getAllProducts() {
        try{
            return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<CreateProductDTO>> getProductsByName(String name) {
            List<CreateProductDTO> products = productService.getProductsByName(name);
            return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CreateProductDTO>> getProductsByCategory(String category) {
        List<CreateProductDTO> products = productService.getProductsByCategory(category);
        return new ResponseEntity<>(products, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> deleteProduct(Long id) {
        try{
            return productService.deteleProduct(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ProductUtils.getResponseEntity(ProductConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
