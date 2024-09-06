package com.test.service;


import com.test.model.ParkingSlot;
import com.test.model.Payment;
import com.test.model.Transport;
import com.test.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final RestTemplate restTemplate;
    private final PaymentRepository paymentRepository;
    private final PriceCalculable regularCalculate;
    private final PriceCalculable vipCalculate;

    public PaymentServiceImpl(RestTemplate restTemplate, PaymentRepository paymentRepository, PriceCalculable regularCalculate, @Qualifier("VIP") PriceCalculable vipCalculate) {
        this.restTemplate = restTemplate;
        this.paymentRepository = paymentRepository;
        this.regularCalculate = regularCalculate;
        this.vipCalculate = vipCalculate;
    }

    @Override
    public Payment createPayment(Payment paymentRequest) {

        try {

            String transportServiceUrl = "http://transport-service/transport/" + paymentRequest.getTransportId();
            Transport transport = restTemplate.getForObject(transportServiceUrl, Transport.class);

            String parkingServiceUrl = "http://parkingslot-service/parkingslot/location/" + paymentRequest.getLocationId();
            ParkingSlot parkingSlot = restTemplate.getForObject(parkingServiceUrl, ParkingSlot.class);

            String parkingServicePutUrl = "http://parkingslot-service/parkingslot/a/" + paymentRequest.getLocationId() + "/false";
            try {
                restTemplate.put(parkingServicePutUrl, null);
            } catch (RestClientException e) {
                throw new RuntimeException("Error reverting parking slot availability", e);
            }

            PriceCalculable priceCalculable = Boolean.TRUE.equals(parkingSlot.isVip()) ? vipCalculate : regularCalculate;


            double totalPrice = priceCalculable.calculateTotalPrice(transport.getType());


            Payment payment = Payment.builder().transportName(transport.getName()).parkingLocation(parkingSlot.getLocation()).totalPrice(totalPrice).paymentDate(LocalDateTime.now()).build();


            return paymentRepository.save(payment);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }


    public void deletePayment(Long id) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            revertAvailability(payment.getLocationId());
            paymentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Payment not found with id " + id);
        }
    }

    private void revertAvailability(Long locationId) {
        String slotUpdateUrl = "http://parkingslot-service/parkingslot/a/" + locationId + "/true";
        try {
            restTemplate.put(slotUpdateUrl, null);
        } catch (RestClientException e) {
            throw new RuntimeException("Error reverting parking slot availability", e);
        }
    }
}


