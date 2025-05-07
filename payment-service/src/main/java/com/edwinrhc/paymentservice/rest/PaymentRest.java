package com.edwinrhc.paymentservice.rest;

import com.edwinrhc.common.dto.ApiResponse;
import com.edwinrhc.paymentservice.dto.payment.CreatePaymentDTO;
import com.edwinrhc.paymentservice.dto.payment.UpdatePaymentDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/payments")
public interface PaymentRest {

    @PostMapping(value="/add")
    ResponseEntity<ApiResponse> createPayment(@RequestBody @Valid CreatePaymentDTO dto);

    @PutMapping(value="/update")
    ResponseEntity<String> updatePayment(@RequestBody @Valid UpdatePaymentDTO dto);

    @GetMapping(value="/get")
    ResponseEntity<List<CreatePaymentDTO>> getAllPayments();

    @GetMapping(value="/{id}")
    ResponseEntity<CreatePaymentDTO> getPaymentById(@PathVariable Long id);

    @GetMapping(value="/status/{status}")
    ResponseEntity<List<CreatePaymentDTO>> getPaymentByStatus(@PathVariable String status);

}
