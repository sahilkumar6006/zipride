package com.zipride.repository;

import com.zipride.enums.RideStatus;
import com.zipride.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByRiderIdOrderByCreatedAtDesc(Long riderId);
    List<Ride> findByDriverIdOrderByCreatedAtDesc(Long driverId);
    Optional<Ride> findByDriverIdAndStatus(Long driverId, RideStatus status);
    List<Ride> findByStatus(RideStatus status);
}
