package com.test.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class CreatedPaymentResponseDto {

    private Long id;

    private String transportName;
    private String parkingLocation;
    private double totalPrice;
    private LocalDateTime paymentDate;
}
