package com.zipride.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "riders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Builder.Default
    private Double rating = 5.0;

    @Builder.Default
    private Integer totalRides = 0;
}
