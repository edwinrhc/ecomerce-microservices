package com.edwinrhc.orderservice.rest;


import com.edwinrhc.common.dto.ApiResponse;
import com.edwinrhc.orderservice.dto.order.CreateOrderDTO;
import com.edwinrhc.orderservice.dto.order.UpdateOrderDTO;
import com.edwinrhc.orderservice.dto.payment.PaymentDTO;
import com.edwinrhc.orderservice.dto.product.ProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/orders")
public interface OrderRest {

    @Operation(summary = "Registro de ordenes", description = "Registra una nueva orden")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Orden Creada")
    })

    @PostMapping(value="/add")
    ResponseEntity<ApiResponse> createOrder(@RequestBody @Valid CreateOrderDTO dto);



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
    ResponseEntity<ApiResponse> deleteOrderById(@PathVariable Long id);


    @GetMapping("/product/{id}")
    ResponseEntity<ProductDTO> obtenerProducto(@PathVariable Long id);

    @PostMapping("/payment/pay")
    ResponseEntity<ApiResponse> createPayment(@RequestBody @Valid PaymentDTO dto);

}
