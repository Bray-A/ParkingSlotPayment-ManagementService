package com.test.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Builder
public class CreatedPaymentResponseDto {

    private Long id;

    private double price;

    private String transportName;
    private String parkingLocation;
    private double totalPrice;
    private LocalDateTime paymentDate;
}
