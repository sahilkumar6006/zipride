package com.zipride.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI zipRideOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ZipRide API")
                        .description("Ride-hailing backend — Auth, Rides, Drivers")
                        .version("v1.0.0"));
    }
}
