package com.test.service;

import com.test.model.Transport;
import com.test.repository.TransportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransportServiceImpl implements TransportService {

    private final TransportRepository transportRepository;

    public TransportServiceImpl(TransportRepository transportRepository) {
        this.transportRepository = transportRepository;
    }

    @Override
    public Transport addTransport(Transport transport) {
        return transportRepository.save(transport);
    }

    @Override
    public Optional<Transport> getTransportById(Long id) {
        return transportRepository.findById(id);
    }

    @Override
    public List<Transport> getAllTransport() {
        return transportRepository.findAll();
}
}
