package com.test.controller;

import com.test.model.CreatedPaymentResponseDto;
import com.test.model.Payment;
import com.test.service.PaymentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/payments")
@Validated
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentServiceImpl paymentServiceImpl;

    public PaymentController(PaymentServiceImpl paymentServiceImpl) {
        this.paymentServiceImpl = paymentServiceImpl;
    }

    @PostMapping("/createPayment")
    public ResponseEntity<?> createPayment(@RequestBody @Valid Payment paymentRequest) {
        logger.info("Received request to create payment: {} ", paymentRequest);

        try {
            Payment createdPayment = paymentServiceImpl.createPayment(paymentRequest);
            CreatedPaymentResponseDto createdPaymentResponseDto = CreatedPaymentResponseDto.builder()
                    .id(createdPayment.getId())
                    .transportName(createdPayment.getTransportName())
                    .parkingLocation(createdPayment.getParkingLocation())
                    .totalPrice(createdPayment.getTotalPrice())
                    .paymentDate(createdPayment.getPaymentDate())
                    .build();

            return ResponseEntity.ok(createdPaymentResponseDto);

        } catch (Exception e) {
            logger.error("Error creating payment", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentServiceImpl.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable Long id) {
        try {
            paymentServiceImpl.deletePayment(id);
            return new ResponseEntity<>("Payment deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting payment: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
