package com.edwinrhc.productservice.serviceImpl;

import com.edwinrhc.productservice.constants.ProductConstants;
import com.edwinrhc.productservice.dto.ProductDTO;
import com.edwinrhc.productservice.entity.Product;
import com.edwinrhc.productservice.exception.ResourceNotFoundException;
import com.edwinrhc.productservice.repository.ProductRepository;
import com.edwinrhc.productservice.service.ProductService;
import com.edwinrhc.productservice.utils.ProductUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ModelMapper modelMapper;




    @Override
    public ResponseEntity<String> createProduct(ProductDTO productDTO) {

        log.info("Iniciando creación de producto: {}", productDTO);
        try{
            Product product = modelMapper.map(productDTO, Product.class);
            Product savedProduct = productRepository.save(product);
            ProductDTO result = modelMapper.map(savedProduct, ProductDTO.class);
            return ProductUtils.getResponseEntity("Successfully Registered",HttpStatus.OK);
        }catch(Exception e){
            log.error("Error al crear el producto", e);
            e.printStackTrace();
//          throw new RuntimeException("Error al crear el producto: "+ e.getMessage(),e);
        }
        return ProductUtils.getResponseEntity(ProductConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        try{
            log.info("Iniciando todos los productos");
            return productRepository.findAll()
                    .stream()
                    .map(product -> modelMapper.map(product,ProductDTO.class))
                    .collect(Collectors.toList());
        }catch(Exception e){
            log.error("Error al listar el producto", e);
            throw new RuntimeException("Error al listar el producto: "+ e.getMessage(),e);
        }

    }

    @Override
    public ProductDTO getProductById(Long id) {
        try{
            log.info("Iniciando busqueda por ID - productos");
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
            return modelMapper.map(product, ProductDTO.class);
        }catch(Exception e){
            throw new RuntimeException("Error buscar el producto por ID: "+ e.getMessage(),e);
        }
    }
}
