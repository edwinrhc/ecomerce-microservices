package com.edwinrhc.productservice.serviceImpl;

import com.edwinrhc.productservice.constants.ProductConstants;
import com.edwinrhc.productservice.dto.product.CreateProductDTO;
import com.edwinrhc.productservice.dto.product.UpdateProductDTO;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public ResponseEntity<String> createProduct(CreateProductDTO createProductDTO) {

        log.info("Iniciando creación de producto: {}", createProductDTO);
        try {
            Product product = modelMapper.map(createProductDTO, Product.class);
            Product savedProduct = productRepository.save(product);
            CreateProductDTO result = modelMapper.map(savedProduct, CreateProductDTO.class);
            return ProductUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error al crear el producto", e);
            e.printStackTrace();
//          throw new RuntimeException("Error al crear el producto: "+ e.getMessage(),e);
        }
        return ProductUtils.getResponseEntity(ProductConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(UpdateProductDTO updateProductDTO) {
        log.info("Iniciado updateProducto: {}", updateProductDTO);
        try {
            Optional<Product> optionalProduct = productRepository.findById(updateProductDTO.getId());
            if (optionalProduct.isEmpty()) {
                return ProductUtils.getResponseEntity("Producto no encontrado", HttpStatus.NOT_FOUND);
            }

            Product existingProduct = optionalProduct.get();

            modelMapper.map(updateProductDTO, existingProduct);
            productRepository.save(existingProduct);
            log.info("Producto actualizado correctamente: {}", existingProduct.getId());
            return ProductUtils.getResponseEntity("Producto actualizado correctamente", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error al actualizar el producto", e);
            e.printStackTrace();
        }

        return ProductUtils.getResponseEntity(ProductConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deteleProduct(Long id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
            productRepository.delete(product);
            return ProductUtils.getResponseEntity("Producto eliminado correctamente", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            log.warn("Intento de eliminar un producto no encontrado: {}", e.getMessage());
            return ProductUtils.getResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error al eliminar el producto", e);
            return ProductUtils.getResponseEntity(ProductConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<CreateProductDTO> getAllProducts() {
        try {
            log.info("Iniciando todos los productos");
            return productRepository.findAll()
                    .stream()
                    .map(product -> modelMapper.map(product, CreateProductDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al listar el producto", e);
            throw new RuntimeException("Error al listar el producto: " + e.getMessage(), e);
        }
    }

    @Override
    public CreateProductDTO getProductById(Long id) {
        try {
            log.info("Iniciando busqueda por ID - productos");
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
            return modelMapper.map(product, CreateProductDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error buscar el producto por ID: " + e.getMessage(), e);
        }
    }


    @Override
    public List<CreateProductDTO> getProductsByName(String name) {

            List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
            if(products.isEmpty()) {
                throw new ResourceNotFoundException("Producto", "nombre", name);
            }
            return products
                    .stream()
                    .map(product -> modelMapper.map(product, CreateProductDTO.class))
                    .collect(Collectors.toList());

    }

    @Override
    public List<CreateProductDTO> getProductsByCategory(String category) {

            List<Product> products = productRepository.findByCategoryIgnoreCase(category);
            if (products.isEmpty()) {
                throw new ResourceNotFoundException("Productos", "categorias", category);
            }
            return products
                    .stream()
                    .map(product -> modelMapper.map(product, CreateProductDTO.class))
                    .collect(Collectors.toList());

    }
}
