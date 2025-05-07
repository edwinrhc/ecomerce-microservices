package com.edwinrhc.orderservice.rest;


import com.edwinrhc.common.dto.ApiResponse;
import com.edwinrhc.orderservice.dto.order.CreateOrderDTO;
import com.edwinrhc.orderservice.dto.order.UpdateOrderDTO;
import com.edwinrhc.orderservice.dto.payment.PaymentDTO;
import com.edwinrhc.orderservice.dto.product.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/orders")
public interface OrderRest {

    @PostMapping(value="/add")
    ResponseEntity<String> createOrder(@RequestBody @Valid CreateOrderDTO dto);

    @PutMapping(value="/update")
    ResponseEntity<String> updateOrder(@RequestBody @Valid UpdateOrderDTO dto);

    @GetMapping(value="/get")
    ResponseEntity<List<CreateOrderDTO>> getAllOrders();

    @GetMapping(value="/order-number/{orderNum}")
    ResponseEntity<List<CreateOrderDTO>> getByOrderNum(@PathVariable String orderNum);

    @GetMapping(value="/status/{orderStatus}")
    ResponseEntity<List<CreateOrderDTO>> getByOrderStatus(@PathVariable String orderStatus);

    @GetMapping(value="/{id}")
    ResponseEntity<CreateOrderDTO> getOrderById(@PathVariable Long id);

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteOrderById(@PathVariable Long id);


    @GetMapping("/product/{id}")
    ResponseEntity<ProductDTO> obtenerProducto(@PathVariable Long id);

    @PostMapping("/payment/pay")
    ResponseEntity<ApiResponse> createPayment(@RequestBody @Valid PaymentDTO dto);

}
