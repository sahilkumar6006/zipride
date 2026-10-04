package com.zipride.service;

import com.zipride.dto.request.LoginRequest;
import com.zipride.dto.request.OtpSendRequest;
import com.zipride.dto.request.OtpVerifyRequest;
import com.zipride.dto.request.RegisterRequest;
import com.zipride.dto.response.UserResponse;

public interface AuthService {
    UserResponse register(RegisterRequest request);
    UserResponse login(LoginRequest request);
    void sendOtp(OtpSendRequest request);
    UserResponse verifyOtp(OtpVerifyRequest request);
}
