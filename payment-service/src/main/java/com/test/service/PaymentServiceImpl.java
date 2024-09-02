package com.test.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.model.Payment;
import com.test.repository.PaymentRepository;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(RestTemplate restTemplate, DiscoveryClient discoveryClient,
                              PaymentRepository paymentRepository) {
        this.restTemplate =  restTemplate;
        this.discoveryClient = discoveryClient;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment createPayment(Payment paymentRequest) {

        try {
            double price = paymentRequest.getPrice();
            String transportServiceUrl = getServiceUrl("transport-service") + "/transport/" + paymentRequest.getTransportId() + "/name";
            String name = restTemplate.getForObject(transportServiceUrl, String.class);

            String parkingServiceUrl = getServiceUrl("parkingslot-service") + "/parkingslot/" + paymentRequest.getLocationId()+ "/location";
            String location = restTemplate.getForObject(parkingServiceUrl, String.class);



            double totalPrice = calculateTotalPrice(name, location, price);

            Payment payment = Payment.builder()
                    .price(price)
                    .transportName(name)
                    .parkingLocation(location)
                    .totalPrice(totalPrice)
                    .paymentDate(LocalDateTime.now())
                    .transportId(paymentRequest.getTransportId())
                    .locationId(paymentRequest.getLocationId())
                    .build();

            return paymentRepository.save(payment);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }


    private String getServiceUrl(String serviceName) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
        if (instances.isEmpty()) {
            throw new IllegalStateException("No instances found for service: " + serviceName);
        }
        return instances.get(0).getUri().toString();
    }

    private double calculateTotalPrice(String name, String location, double price) {

        double nameFactor = (name != null) ? name.length() * 10 : 0;
        double locationFactor = (location != null) ? location.length() * 5 : 0;
        return price + nameFactor + locationFactor;
    }
}
