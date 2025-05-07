package com.edwinrhc.paymentservice.dto.payment;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentDTO {


    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor que cero")
    private BigDecimal amount;


    @NotBlank(message = "El paymentMethod no puede estar vacío")
    private String paymentMethod; // tipo de pago

    @NotBlank(message = "El paymentStatus no puede estar vacío")
    private String paymentStatus;

    private LocalDateTime paymentDate;
}
