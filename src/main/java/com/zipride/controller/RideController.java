package com.zipride.controller;

import com.zipride.dto.request.RatingRequest;
import com.zipride.dto.request.RideRequestDto;
import com.zipride.dto.response.ApiResponse;
import com.zipride.dto.response.RideResponse;
import com.zipride.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping("/request")
    public ApiResponse<RideResponse> requestRide(
            @RequestParam Long riderId,
            @RequestBody RideRequestDto dto) {
        return ApiResponse.ok(rideService.requestRide(riderId, dto));
    }

    @PostMapping("/{rideId}/accept")
    public ApiResponse<RideResponse> acceptRide(
            @PathVariable Long rideId,
            @RequestParam Long driverId) {
        return ApiResponse.ok(rideService.acceptRide(rideId, driverId));
    }

    @PostMapping("/{rideId}/arrived")
    public ApiResponse<RideResponse> driverArrived(
            @PathVariable Long rideId,
            @RequestParam Long driverId) {
        return ApiResponse.ok(rideService.markDriverArrived(rideId, driverId));
    }

    @PostMapping("/{rideId}/start")
    public ApiResponse<RideResponse> startRide(
            @PathVariable Long rideId,
            @RequestParam Long driverId) {
        return ApiResponse.ok(rideService.startRide(rideId, driverId));
    }

    @PostMapping("/{rideId}/complete")
    public ApiResponse<RideResponse> completeRide(
            @PathVariable Long rideId,
            @RequestParam Long driverId) {
        return ApiResponse.ok(rideService.completeRide(rideId, driverId));
    }

    @PostMapping("/{rideId}/cancel")
    public ApiResponse<RideResponse> cancelRide(
            @PathVariable Long rideId,
            @RequestParam(required = false) String reason) {
        return ApiResponse.ok(rideService.cancelRide(rideId, reason));
    }

    @GetMapping("/{rideId}")
    public ApiResponse<RideResponse> getRide(@PathVariable Long rideId) {
        return ApiResponse.ok(rideService.getRide(rideId));
    }

    @GetMapping("/history/rider/{riderId}")
    public ApiResponse<List<RideResponse>> riderHistory(@PathVariable Long riderId) {
        return ApiResponse.ok(rideService.getRiderHistory(riderId));
    }

    @GetMapping("/history/driver/{driverId}")
    public ApiResponse<List<RideResponse>> driverHistory(@PathVariable Long driverId) {
        return ApiResponse.ok(rideService.getDriverHistory(driverId));
    }

    @PostMapping("/rate")
    public ApiResponse<Void> rate(
            @RequestParam Long userId,
            @RequestBody RatingRequest request) {
        rideService.submitRating(userId, request);
        return ApiResponse.ok("Rating submitted", null);
    }
}
