package com.zipride.controller;

import com.zipride.dto.request.LoginRequest;
import com.zipride.dto.request.OtpSendRequest;
import com.zipride.dto.request.OtpVerifyRequest;
import com.zipride.dto.request.RegisterRequest;
import com.zipride.dto.response.ApiResponse;
import com.zipride.dto.response.UserResponse;
import com.zipride.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@RequestBody RegisterRequest request) {
        return ApiResponse.ok("Registered successfully", authService.register(request));
    }

    @PostMapping("/login")
    public ApiResponse<UserResponse> login(@RequestBody LoginRequest request) {
        return ApiResponse.ok(authService.login(request));
    }

    @PostMapping("/otp/send")
    public ApiResponse<Void> sendOtp(@RequestBody OtpSendRequest request) {
        authService.sendOtp(request);
        return ApiResponse.ok("OTP sent successfully (use 123456)", null);
    }

    @PostMapping("/otp/verify")
    public ApiResponse<UserResponse> verifyOtp(@RequestBody OtpVerifyRequest request) {
        return ApiResponse.ok("Login successful", authService.verifyOtp(request));
    }
}
