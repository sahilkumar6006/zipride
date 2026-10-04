package com.zipride.dto.request;

import lombok.Data;

@Data
public class RatingRequest {
    private Long rideId;
    private Integer score;      // 1–5
    private String comment;
}
