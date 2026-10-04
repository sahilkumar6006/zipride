package com.zipride.repository;

import com.zipride.enums.DriverStatus;
import com.zipride.enums.VehicleType;
import com.zipride.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    Optional<Driver> findByUserId(Long userId);

    List<Driver> findByStatus(DriverStatus status);

    @Query("""
        SELECT d FROM Driver d
        JOIN Vehicle v ON v.driver = d
        WHERE d.status = 'ONLINE'
          AND v.vehicleType = :vehicleType
          AND v.isActive = true
          AND (6371 * acos(
                cos(radians(:lat)) * cos(radians(d.currentLat))
                * cos(radians(d.currentLng) - radians(:lng))
                + sin(radians(:lat)) * sin(radians(d.currentLat))
              )) < :radiusKm
        ORDER BY d.rating DESC
        """)
    List<Driver> findNearbyDrivers(
        @Param("lat") double lat,
        @Param("lng") double lng,
        @Param("radiusKm") double radiusKm,
        @Param("vehicleType") VehicleType vehicleType
    );
}
