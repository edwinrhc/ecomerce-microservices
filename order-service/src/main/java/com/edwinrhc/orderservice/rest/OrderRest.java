package com.edwinrhc.orderservice.rest;


import com.edwinrhc.orderservice.dto.order.CreateOrderDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/orders")
public interface OrderRest {

    @PostMapping(value="/add")
    ResponseEntity<String> createOrder(@RequestBody @Valid CreateOrderDTO dto);

}
