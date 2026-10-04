package com.zipride.model;

import com.zipride.enums.DriverStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "drivers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(unique = true)
    private String licenseNumber;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private DriverStatus status = DriverStatus.OFFLINE;

    private Double currentLat;
    private Double currentLng;

    @Builder.Default
    private Double rating = 5.0;

    @Builder.Default
    private Integer totalRides = 0;
}
