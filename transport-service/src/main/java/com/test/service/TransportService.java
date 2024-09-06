package com.test.service;

import com.test.model.Transport;

import java.util.List;
import java.util.Optional;

public interface TransportService {

    Transport addTransport(Transport transport);

    Optional<Transport> getTransportById(Long id);

    List<Transport> getAllTransport();
}
