package com.test.controller;

import com.test.model.ParkingSlot;
import com.test.service.ParkingSlotService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parkingslot")
public class ParkingSlotController {

    private ParkingSlotService parkingSlotService;

    public ParkingSlotController(ParkingSlotService parkingSlotService) {
        this.parkingSlotService = parkingSlotService;
    }

    @GetMapping("/get")
    public List<ParkingSlot> getAllSlots() {
        return parkingSlotService.getAllSlots();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSlot> getSlotById(@PathVariable Long id){
        Optional<ParkingSlot> parkingSlot = parkingSlotService.getSlotById(id);
        return parkingSlot.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<ParkingSlot> createSlot(@RequestBody ParkingSlot parkingSlot){
        System.out.println("create");
        ParkingSlot createdSlot = parkingSlotService.createSlot(parkingSlot);
        return ResponseEntity.ok(createdSlot);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSlot> updateSlot(@PathVariable Long id, @RequestBody ParkingSlot parkingSlot) {
        if (parkingSlotService.getSlotById(id).isPresent()) {
            parkingSlot.setId(id);
            ParkingSlot updatedSlot = parkingSlotService.updateSlot(id, parkingSlot);
            return ResponseEntity.ok(updatedSlot);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSlot(@PathVariable Long id) {
        if (parkingSlotService.getSlotById(id).isPresent()) {
            parkingSlotService.deleteSlot(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/location")
    public ResponseEntity<String> getLocation(@PathVariable Long id) {
        Optional<String> location = parkingSlotService.getLocationById(id);
        return location.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    }

