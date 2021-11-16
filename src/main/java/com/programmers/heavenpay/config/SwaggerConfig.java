package com.programmers.heavenpay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final String CONTACT_NAME = "HEAVEN PAY";
    private static final String CONTACT_URL = "https://github.com/prgrms-be-devcourse/BEDV1_Heaven-Pay";
    private static final String CONTACT_EMAIL = "wrjs@naver.com";
    private static final String API_INFO_TITLE = "Heacen Pay REST API";
    private static final String API_INFO_DESCRIPTION = "Management REST API Service";
    private static final String API_INFO_VERSION = "1.0";
    private static final String API_INFO_TERMS_OF_SERVICE_URL = "urn:tos";
    private static final String API_INFO_LICENSE = "Apache 2.0";
    private static final String API_INFO_LICENSE_URL = "http://www.apache.org/licenses/LICENSE-2.0";
    private static final String[] APPLICATION_CONFIG_KIND = {"application/json", "application/xml"};

    private static final Contact DEFAULT_CONTACT = new Contact(
            CONTACT_NAME,
            CONTACT_URL,
            CONTACT_EMAIL
    );

    private static final ApiInfo Default_API_INFO = new ApiInfo(
            API_INFO_TITLE,
            API_INFO_DESCRIPTION,
            API_INFO_VERSION,
            API_INFO_TERMS_OF_SERVICE_URL,
            DEFAULT_CONTACT,
            API_INFO_LICENSE,
            API_INFO_LICENSE_URL,
            new ArrayList<>()
    );

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(
            Arrays.asList(APPLICATION_CONFIG_KIND)
    );

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(Default_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }
}