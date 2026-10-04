package com.zipride.dto.response;

import com.zipride.enums.DriverStatus;
import com.zipride.enums.VehicleType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DriverResponse {
    private Long id;
    private String name;
    private String phone;
    private Double rating;
    private Integer totalRides;
    private DriverStatus status;
    private VehicleType vehicleType;
    private String vehiclePlate;
    private String vehicleModel;
}
