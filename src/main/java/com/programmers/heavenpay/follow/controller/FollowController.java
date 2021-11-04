package com.programmers.heavenpay.follow.controller;

import com.programmers.heavenpay.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/follows", produces = MediaTypes.HAL_JSON_VALUE)
public class FollowController {
    private final FollowService followService;
}
