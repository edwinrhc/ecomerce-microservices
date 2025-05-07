package com.edwinrhc.orderservice.service;


import com.edwinrhc.common.dto.ApiResponse;
import com.edwinrhc.orderservice.dto.order.CreateOrderDTO;
import com.edwinrhc.orderservice.dto.order.UpdateOrderDTO;
import com.edwinrhc.orderservice.dto.payment.PaymentDTO;
import com.edwinrhc.orderservice.dto.product.ProductDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {

    ResponseEntity<String> createOrder(CreateOrderDTO createOrderDTO);
    ResponseEntity<String> updateOrder(UpdateOrderDTO updateOrderDTO);
    ResponseEntity<String> deleteOrder(Long orderId);

    ResponseEntity<CreateOrderDTO> getOrderById(Long orderId);

    List<CreateOrderDTO> getAllOrders();
    List<CreateOrderDTO> getOrdersByNumber(String orderNumber);
    List<CreateOrderDTO> getOrdersByStatus(String orderStatus);


    ProductDTO obtenerProductoPorId(Long id);

    ResponseEntity<ApiResponse> createPayment(PaymentDTO paymentDTO);
}
