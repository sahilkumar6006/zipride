package com.zipride.service;

import com.zipride.dto.request.DriverLocationUpdateDto;
import com.zipride.dto.response.DriverResponse;
import com.zipride.enums.DriverStatus;

public interface DriverService {
    DriverResponse getProfile(Long driverId);
    void updateLocation(Long driverId, DriverLocationUpdateDto dto);
    DriverResponse updateStatus(Long driverId, DriverStatus status);
}
