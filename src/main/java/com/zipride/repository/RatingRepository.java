package com.zipride.repository;

import com.zipride.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByRatedUserId(Long userId);

    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.ratedUser.id = :userId")
    Double findAverageRatingByUserId(@Param("userId") Long userId);
}
