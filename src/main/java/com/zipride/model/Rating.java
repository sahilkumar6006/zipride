package com.zipride.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ride_id", nullable = false, unique = true)
    private Ride ride;

    @ManyToOne
    @JoinColumn(name = "rated_by_user_id", nullable = false)
    private User ratedBy;

    @ManyToOne
    @JoinColumn(name = "rated_user_id", nullable = false)
    private User ratedUser;

    @Column(nullable = false)
    private Integer score;  // 1–5

    private String comment;

    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
