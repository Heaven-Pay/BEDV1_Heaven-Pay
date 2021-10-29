package com.programmers.heavenpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class HeavenpayApplication {
    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.yml,"
            + "classpath:aws.yml";

    public static void main(String[] args) {
        //SpringApplication.run(HeavenpayApplication.class, args);

        new SpringApplicationBuilder(HeavenpayApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }
}
