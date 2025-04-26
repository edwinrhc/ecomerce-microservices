package com.edwinrhc.orderservice.repository;

import com.edwinrhc.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findByOrderNumberIgnoreCase(String orderNumber);

    List<Order> findByOrderStatusIgnoreCase(String orderStatus);
}
