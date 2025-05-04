package com.edwinrhc.orderservice.dto.order;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreateOrderDTO {


    private Long id;

//    @NotBlank(message = "El orderNumber no puede estar vacío")
    @Column(unique = true)
    private String orderNumber;

    private LocalDateTime orderDate;

//    @NotNull(message = "El totalAmount es obligatorio")
//    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que cero")
    private BigDecimal totalAmount;

//    @NotBlank(message = "El orderStatus no puede estar vacío")
    private String orderStatus;

    private Long productId;
    private Integer productQuantity;


}
