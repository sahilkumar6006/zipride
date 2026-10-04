package com.zipride.model;

import com.zipride.enums.RideStatus;
import com.zipride.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rides")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rider_id", nullable = false)
    private Rider rider;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private RideStatus status = RideStatus.REQUESTED;

    @Column(nullable = false)
    private String pickupAddress;
    private Double pickupLat;
    private Double pickupLng;

    @Column(nullable = false)
    private String dropAddress;
    private Double dropLat;
    private Double dropLng;

    private Double fare;
    private Double distanceKm;

    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private LocalDateTime cancelledAt;
    private String cancelReason;

    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
