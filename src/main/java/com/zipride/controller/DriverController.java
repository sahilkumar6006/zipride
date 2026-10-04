package com.zipride.controller;

import com.zipride.dto.request.DriverLocationUpdateDto;
import com.zipride.dto.response.ApiResponse;
import com.zipride.dto.response.DriverResponse;
import com.zipride.enums.DriverStatus;
import com.zipride.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @GetMapping("/{driverId}/profile")
    public ApiResponse<DriverResponse> getProfile(@PathVariable Long driverId) {
        return ApiResponse.ok(driverService.getProfile(driverId));
    }

    @PutMapping("/{driverId}/location")
    public ApiResponse<Void> updateLocation(
            @PathVariable Long driverId,
            @RequestBody DriverLocationUpdateDto dto) {
        driverService.updateLocation(driverId, dto);
        return ApiResponse.ok("Location updated", null);
    }

    @PutMapping("/{driverId}/status")
    public ApiResponse<DriverResponse> updateStatus(
            @PathVariable Long driverId,
            @RequestParam DriverStatus status) {
        return ApiResponse.ok(driverService.updateStatus(driverId, status));
    }
}
