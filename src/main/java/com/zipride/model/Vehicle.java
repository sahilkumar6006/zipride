package com.zipride.model;

import com.zipride.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;

    private String make;        // e.g. Honda
    private String model;       // e.g. Activa
    private Integer year;
    private String color;

    @Column(unique = true, nullable = false)
    private String plateNumber;

    @Builder.Default
    private Boolean isActive = true;
}
