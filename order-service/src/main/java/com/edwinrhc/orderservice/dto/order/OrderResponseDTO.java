package com.edwinrhc.orderservice.dto.order;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderResponseDTO {
    private Long id;
    private String orderNumber;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String orderStatus;
    private Long productId;
    private Integer productQuantity;
}
