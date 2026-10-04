package com.zipride.dto.request;

import lombok.Data;

@Data
public class OtpVerifyRequest {
    private String phone;
    private String otp;
}
