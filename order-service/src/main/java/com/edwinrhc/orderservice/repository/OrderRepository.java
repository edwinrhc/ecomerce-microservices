package com.edwinrhc.orderservice.repository;

import com.edwinrhc.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value="SELECT o.order_number FROM orders o ORDER BY o.id DESC LIMIT 1",nativeQuery = true)
    String findLastOrderNumber();


    List<Order> findByOrderNumberIgnoreCase(String orderNumber);

    List<Order> findByOrderStatusIgnoreCase(String orderStatus);
}
