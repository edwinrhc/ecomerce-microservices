package com.edwinrhc.paymentservice.dto.payment;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreatePaymentDTO {


    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false, length = 50)
    private String paymentMethod; // tipo de pago

    @Column(nullable = false, length = 20)
    private String paymentStatus;

    @Column(nullable = false)
    private LocalDateTime paymentDate;
}
