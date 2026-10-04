package com.zipride.repository;

import com.zipride.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RiderRepository extends JpaRepository<Rider, Long> {
    Optional<Rider> findByUserId(Long userId);
}
