package com.zipride.service.impl;

import com.zipride.dto.request.DriverLocationUpdateDto;
import com.zipride.dto.response.DriverResponse;
import com.zipride.enums.DriverStatus;
import com.zipride.exception.ResourceNotFoundException;
import com.zipride.model.Driver;
import com.zipride.model.Vehicle;
import com.zipride.repository.DriverRepository;
import com.zipride.repository.VehicleRepository;
import com.zipride.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public DriverResponse getProfile(Long driverId) {
        return toResponse(getOrThrow(driverId));
    }

    @Override
    @Transactional
    public void updateLocation(Long driverId, DriverLocationUpdateDto dto) {
        Driver driver = getOrThrow(driverId);
        driver.setCurrentLat(dto.getLat());
        driver.setCurrentLng(dto.getLng());
        driverRepository.save(driver);
    }

    @Override
    @Transactional
    public DriverResponse updateStatus(Long driverId, DriverStatus status) {
        Driver driver = getOrThrow(driverId);
        driver.setStatus(status);
        return toResponse(driverRepository.save(driver));
    }

    private Driver getOrThrow(Long driverId) {
        return driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found: " + driverId));
    }

    private DriverResponse toResponse(Driver d) {
        Vehicle vehicle = vehicleRepository.findByDriverIdAndIsActiveTrue(d.getId()).orElse(null);
        return DriverResponse.builder()
                .id(d.getId())
                .name(d.getUser().getName())
                .phone(d.getUser().getPhone())
                .rating(d.getRating())
                .totalRides(d.getTotalRides())
                .status(d.getStatus())
                .vehicleType(vehicle != null ? vehicle.getVehicleType() : null)
                .vehiclePlate(vehicle != null ? vehicle.getPlateNumber() : null)
                .vehicleModel(vehicle != null ? vehicle.getModel() : null)
                .build();
    }
}
