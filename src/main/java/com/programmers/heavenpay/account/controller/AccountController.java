package com.programmers.heavenpay.account.controller;

import com.programmers.heavenpay.account.dto.request.AccountCreateRequest;
import com.programmers.heavenpay.account.dto.response.AccountCreateResponse;
import com.programmers.heavenpay.account.service.AccountService;
import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Api(tags = "Account")
@RestController
@RequestMapping(value = "/api/v1/accounts", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final ResponseConverter responseConverter;

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(AccountController.class);
    }

    @ApiOperation("계좌 생성")
    @PostMapping(consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> create(@Valid @RequestBody AccountCreateRequest request) {
        AccountCreateResponse response = accountService.create(
                request.getMemberId(),
                request.getTitle(),
                request.getDescription(),
                request.getNumber(),
                request.getFinanceId()
        );

        EntityModel<AccountCreateResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.ACCOUNT_CREATE_SUCCESS,
                entityModel
        );
    }
}
