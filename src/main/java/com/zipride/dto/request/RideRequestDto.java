package com.zipride.dto.request;

import com.zipride.enums.VehicleType;
import lombok.Data;

@Data
public class RideRequestDto {
    private String pickupAddress;
    private Double pickupLat;
    private Double pickupLng;
    private String dropAddress;
    private Double dropLat;
    private Double dropLng;
    private VehicleType vehicleType;
}
