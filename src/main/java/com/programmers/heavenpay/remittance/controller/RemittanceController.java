package com.programmers.heavenpay.remittance.controller;

import com.programmers.heavenpay.remittance.service.RemittanceService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Remittance")
@RestController
@RequestMapping(value = "/api/v1/remittances", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class RemittanceController {
    private final RemittanceService remittanceService;
}
