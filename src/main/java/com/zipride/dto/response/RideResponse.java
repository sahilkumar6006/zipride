package com.zipride.dto.response;

import com.zipride.enums.RideStatus;
import com.zipride.enums.VehicleType;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class RideResponse {
    private Long id;
    private Long riderId;
    private Long driverId;
    private VehicleType vehicleType;
    private RideStatus status;
    private String pickupAddress;
    private String dropAddress;
    private Double fare;
    private Double distanceKm;
    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
}
