package com.programmers.heavenpay;

import com.programmers.heavenpay.config.ApplicationConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class HeavenpayApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(HeavenpayApplication.class)
                .properties(ApplicationConfig.APPLICATION_LOCATIONS)
                .run(args);
    }
}