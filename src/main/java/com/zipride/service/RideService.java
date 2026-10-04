package com.zipride.service;

import com.zipride.dto.request.RatingRequest;
import com.zipride.dto.request.RideRequestDto;
import com.zipride.dto.response.RideResponse;
import java.util.List;

public interface RideService {
    RideResponse requestRide(Long riderId, RideRequestDto dto);
    RideResponse acceptRide(Long rideId, Long driverId);
    RideResponse markDriverArrived(Long rideId, Long driverId);
    RideResponse startRide(Long rideId, Long driverId);
    RideResponse completeRide(Long rideId, Long driverId);
    RideResponse cancelRide(Long rideId, String reason);
    RideResponse getRide(Long rideId);
    List<RideResponse> getRiderHistory(Long riderId);
    List<RideResponse> getDriverHistory(Long driverId);
    void submitRating(Long userId, RatingRequest request);
}
