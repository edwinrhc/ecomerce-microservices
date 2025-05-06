package com.edwinrhc.orderservice.feign;


import com.edwinrhc.orderservice.dto.payment.PaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="payment-service", path="/api/payments")
public interface PaymentClient {

    @PostMapping("/add")
    ResponseEntity<String> createPayment(@RequestBody  PaymentDTO paymentDTO);

    }

