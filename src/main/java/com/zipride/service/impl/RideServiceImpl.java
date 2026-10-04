package com.zipride.service.impl;

import com.zipride.dto.request.RatingRequest;
import com.zipride.dto.request.RideRequestDto;
import com.zipride.dto.response.RideResponse;
import com.zipride.enums.RideStatus;
import com.zipride.exception.ResourceNotFoundException;
import com.zipride.exception.ZipRideException;
import com.zipride.model.*;
import com.zipride.repository.*;
import com.zipride.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final RiderRepository riderRepository;
    private final DriverRepository driverRepository;
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public RideResponse requestRide(Long riderId, RideRequestDto dto) {
        Rider rider = riderRepository.findById(riderId)
                .orElseThrow(() -> new ResourceNotFoundException("Rider not found"));

        Ride ride = rideRepository.save(Ride.builder()
                .rider(rider)
                .vehicleType(dto.getVehicleType())
                .pickupAddress(dto.getPickupAddress())
                .pickupLat(dto.getPickupLat())
                .pickupLng(dto.getPickupLng())
                .dropAddress(dto.getDropAddress())
                .dropLat(dto.getDropLat())
                .dropLng(dto.getDropLng())
                .build());

        return toResponse(ride);
    }

    @Override
    @Transactional
    public RideResponse acceptRide(Long rideId, Long driverId) {
        Ride ride = getOrThrow(rideId);
        assertStatus(ride, RideStatus.REQUESTED);

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        ride.setDriver(driver);
        ride.setStatus(RideStatus.ACCEPTED);
        return toResponse(rideRepository.save(ride));
    }

    @Override
    @Transactional
    public RideResponse markDriverArrived(Long rideId, Long driverId) {
        Ride ride = getOrThrow(rideId);
        assertStatus(ride, RideStatus.ACCEPTED);
        ride.setStatus(RideStatus.DRIVER_ARRIVED);
        return toResponse(rideRepository.save(ride));
    }

    @Override
    @Transactional
    public RideResponse startRide(Long rideId, Long driverId) {
        Ride ride = getOrThrow(rideId);
        assertStatus(ride, RideStatus.DRIVER_ARRIVED);
        ride.setStatus(RideStatus.IN_PROGRESS);
        ride.setStartedAt(LocalDateTime.now());
        return toResponse(rideRepository.save(ride));
    }

    @Override
    @Transactional
    public RideResponse completeRide(Long rideId, Long driverId) {
        Ride ride = getOrThrow(rideId);
        assertStatus(ride, RideStatus.IN_PROGRESS);
        ride.setStatus(RideStatus.COMPLETED);
        ride.setCompletedAt(LocalDateTime.now());
        return toResponse(rideRepository.save(ride));
    }

    @Override
    @Transactional
    public RideResponse cancelRide(Long rideId, String reason) {
        Ride ride = getOrThrow(rideId);
        if (ride.getStatus() == RideStatus.COMPLETED || ride.getStatus() == RideStatus.CANCELLED) {
            throw new ZipRideException("Cannot cancel a " + ride.getStatus() + " ride");
        }
        ride.setStatus(RideStatus.CANCELLED);
        ride.setCancelReason(reason);
        ride.setCancelledAt(LocalDateTime.now());
        return toResponse(rideRepository.save(ride));
    }

    @Override
    public RideResponse getRide(Long rideId) {
        return toResponse(getOrThrow(rideId));
    }

    @Override
    public List<RideResponse> getRiderHistory(Long riderId) {
        return rideRepository.findByRiderIdOrderByCreatedAtDesc(riderId)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public List<RideResponse> getDriverHistory(Long driverId) {
        return rideRepository.findByDriverIdOrderByCreatedAtDesc(driverId)
                .stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public void submitRating(Long userId, RatingRequest req) {
        Ride ride = getOrThrow(req.getRideId());
        assertStatus(ride, RideStatus.COMPLETED);

        User rater = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean raterIsRider = ride.getRider().getUser().getId().equals(userId);
        User ratee = raterIsRider
                ? ride.getDriver().getUser()
                : ride.getRider().getUser();

        ratingRepository.save(Rating.builder()
                .ride(ride)
                .ratedBy(rater)
                .ratedUser(ratee)
                .score(req.getScore())
                .comment(req.getComment())
                .build());
    }

    private Ride getOrThrow(Long rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found: " + rideId));
    }

    private void assertStatus(Ride ride, RideStatus expected) {
        if (ride.getStatus() != expected) {
            throw new ZipRideException("Expected ride status " + expected + " but was " + ride.getStatus());
        }
    }

    private RideResponse toResponse(Ride r) {
        return RideResponse.builder()
                .id(r.getId())
                .riderId(r.getRider().getId())
                .driverId(r.getDriver() != null ? r.getDriver().getId() : null)
                .vehicleType(r.getVehicleType())
                .status(r.getStatus())
                .pickupAddress(r.getPickupAddress())
                .dropAddress(r.getDropAddress())
                .fare(r.getFare())
                .distanceKm(r.getDistanceKm())
                .createdAt(r.getCreatedAt())
                .startedAt(r.getStartedAt())
                .completedAt(r.getCompletedAt())
                .build();
    }
}
