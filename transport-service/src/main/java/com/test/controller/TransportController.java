package com.test.controller;

import com.test.model.Transport;
import com.test.service.TransportService;
import com.test.service.TransportServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transport")
public class TransportController {

    private final TransportService transportService;

    public TransportController(TransportService transportService) {
        this.transportService = transportService;
    }

    @PostMapping("/create")
    public ResponseEntity<Transport> createTransport(@RequestBody Transport transport) {
        Transport createdTransport = transportService.addTransport(transport);
        return new ResponseEntity<>(createdTransport, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transport> getName(@PathVariable Long id) {
        Optional<Transport> transport = transportService.getTransportById(id);
        return transport.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<Transport> getAllTransport () {
        return transportService.getAllTransport();
    }

    }
