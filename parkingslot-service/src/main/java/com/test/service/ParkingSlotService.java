package com.test.service;

import com.test.model.ParkingSlot;

import java.util.List;
import java.util.Optional;

public interface ParkingSlotService {

    List<ParkingSlot> getAllSlots();

    Optional<ParkingSlot> getSlotById(Long id);

    ParkingSlot createSlot(ParkingSlot slot);

    ParkingSlot updateSlot(Long id, ParkingSlot parkingSlot);

    void deleteSlot(Long id);

    Optional<String> getLocationById(Long id);
}
