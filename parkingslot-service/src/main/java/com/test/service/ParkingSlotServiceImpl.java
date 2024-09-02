package com.test.service;

import com.test.model.ParkingSlot;
import com.test.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingSlotServiceImpl implements ParkingSlotService {

    private ParkingSlotRepository parkingSlotRepository;


    public ParkingSlotServiceImpl(ParkingSlotRepository parkingSlotRepository){
        this.parkingSlotRepository = parkingSlotRepository;
    }

    @Override
    public List<ParkingSlot> getAllSlots() {
        return parkingSlotRepository.findAll();
    }

    @Override
    public Optional<ParkingSlot> getSlotById(Long id) {
        return parkingSlotRepository.findById(id);
    }


    @Override
    public ParkingSlot createSlot(ParkingSlot slot) {
        ParkingSlot tempParkingSlot = parkingSlotRepository.save(slot);
        return tempParkingSlot;
    }

    @Override
    public ParkingSlot updateSlot(Long id, ParkingSlot parkingSlot) {
        return parkingSlotRepository.findById(id)
                .map(existingSlot -> {
                        existingSlot.setLocation(parkingSlot.getLocation());
        return parkingSlotRepository.save(existingSlot);
                })
                .orElseThrow(() -> new IllegalArgumentException("Slot with Id " + id + " does not exists."));
    }

    @Override
    public void deleteSlot(Long id) {
        parkingSlotRepository.deleteById(id);
    }

    @Override
    public Optional<String> getLocationById(Long id) {
        return parkingSlotRepository.findById(id)
                .map(ParkingSlot::getLocation);
    }
}
