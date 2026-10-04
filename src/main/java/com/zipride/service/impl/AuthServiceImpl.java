package com.zipride.service.impl;

import com.zipride.dto.request.LoginRequest;
import com.zipride.dto.request.OtpSendRequest;
import com.zipride.dto.request.OtpVerifyRequest;
import com.zipride.dto.request.RegisterRequest;
import com.zipride.dto.response.UserResponse;
import com.zipride.enums.UserRole;
import com.zipride.exception.ResourceNotFoundException;
import com.zipride.exception.ZipRideException;
import com.zipride.model.Driver;
import com.zipride.model.Rider;
import com.zipride.model.User;
import com.zipride.repository.DriverRepository;
import com.zipride.repository.RiderRepository;
import com.zipride.repository.UserRepository;
import com.zipride.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String STATIC_OTP = "123456";

    private final UserRepository userRepository;
    private final RiderRepository riderRepository;
    private final DriverRepository driverRepository;

    // phone -> otp (in-memory store; replace with Redis/DB for production)
    private final Map<String, String> otpStore = new ConcurrentHashMap<>();

    @Override
    @Transactional
    public UserResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new ZipRideException("Email already registered");
        }
        if (userRepository.existsByPhone(req.getPhone())) {
            throw new ZipRideException("Phone already registered");
        }

        User user = userRepository.save(User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .phone(req.getPhone())
                .passwordHash(req.getPassword())    // TODO: BCrypt before prod
                .role(req.getRole())
                .build());

        if (req.getRole() == UserRole.RIDER) {
            riderRepository.save(Rider.builder().user(user).build());
        } else {
            driverRepository.save(Driver.builder()
                    .user(user)
                    .licenseNumber(req.getLicenseNumber())
                    .build());
        }

        return toResponse(user);
    }

    @Override
    public UserResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.getPasswordHash().equals(req.getPassword())) {
            throw new ZipRideException("Invalid credentials");
        }

        return toResponse(user);
    }

    @Override
    public void sendOtp(OtpSendRequest req) {
        if (!userRepository.existsByPhone(req.getPhone())) {
            throw new ResourceNotFoundException("No user found with this phone number");
        }
        // Static OTP — replace with SMS provider in production
        otpStore.put(req.getPhone(), STATIC_OTP);
    }

    @Override
    public UserResponse verifyOtp(OtpVerifyRequest req) {
        String expected = otpStore.get(req.getPhone());
        if (expected == null) {
            throw new ZipRideException("OTP not requested for this phone");
        }
        if (!expected.equals(req.getOtp())) {
            throw new ZipRideException("Invalid OTP");
        }
        otpStore.remove(req.getPhone());

        User user = userRepository.findByPhone(req.getPhone())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return toResponse(user);
    }

    private UserResponse toResponse(User u) {
        return UserResponse.builder()
                .id(u.getId())
                .name(u.getName())
                .email(u.getEmail())
                .phone(u.getPhone())
                .role(u.getRole())
                .build();
    }
}
