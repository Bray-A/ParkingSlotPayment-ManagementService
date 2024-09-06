package com.test.controller;

import com.test.model.ParkingSlot;
import com.test.service.ParkingSlotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parkingslot")
public class ParkingSlotController {

    private final ParkingSlotService parkingSlotService;

    public ParkingSlotController(ParkingSlotService parkingSlotService) {
        this.parkingSlotService = parkingSlotService;
    }

    @GetMapping("/get")
    public List<ParkingSlot> getAllSlots() {
        return parkingSlotService.getAllSlots();
    }

    @GetMapping("/location/{id}")
    public ResponseEntity<ParkingSlot> getLocation(@PathVariable Long id) {
        Optional<ParkingSlot> parkingSlot = parkingSlotService.getLocationById(id);
        return parkingSlot.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<ParkingSlot> createSlot(@RequestBody ParkingSlot parkingSlot){
        ParkingSlot createdSlot = parkingSlotService.createSlot(parkingSlot);
        return ResponseEntity.ok(createdSlot);
    }



    @PutMapping("/a/{id}/{availability}")
    public ResponseEntity<ParkingSlot> updateSlotAvailability(@PathVariable Long id, @PathVariable boolean availability) {
        Optional<ParkingSlot> tempParkingSlot = parkingSlotService.getSlotById(id);
        if (tempParkingSlot.isPresent()) {
            tempParkingSlot.get().setAvailability(availability);
            ParkingSlot updatedSlot = parkingSlotService.updateSlot(id, tempParkingSlot.get());
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


    }

