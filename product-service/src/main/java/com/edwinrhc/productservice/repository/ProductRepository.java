package com.edwinrhc.productservice.repository;

import com.edwinrhc.productservice.entity.Product;
import jakarta.transaction.Transactional;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {


    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.name = :name, p.description = :description, p.price = :price, p.stock= :stock, p.category = :category WHERE p.id = :id ")
    Long updateProduct(@Param("name") String name,
                       @Param("description") String description,
                       @Param("price") BigDecimal price,
                       @Param("stock") Integer stock,
                       @Param("category") String category,
                       @Param("id") Long id
                       );

}
