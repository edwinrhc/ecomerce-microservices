package com.edwinrhc.paymentservice.service;

import com.edwinrhc.common.dto.ApiResponse;
import com.edwinrhc.paymentservice.dto.payment.CreatePaymentDTO;
import com.edwinrhc.paymentservice.dto.payment.UpdatePaymentDTO;
import com.edwinrhc.paymentservice.entity.Payment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PaymentService {

    ResponseEntity<ApiResponse> createPayment(CreatePaymentDTO createPaymentDTO);


    ResponseEntity<String> updatePayment(UpdatePaymentDTO updatePaymentDTO);

    ResponseEntity<CreatePaymentDTO> getPaymentById(Long paymentId);

    List<CreatePaymentDTO> getAllPayments();
    List<CreatePaymentDTO> getPaymentsStatus(String status);

}
