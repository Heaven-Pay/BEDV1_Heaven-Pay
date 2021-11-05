package com.programmers.heavenpay.account.controller;

import com.programmers.heavenpay.account.dto.request.AccountCreateRequest;
import com.programmers.heavenpay.account.dto.request.AccountDeleteRequest;
import com.programmers.heavenpay.account.dto.request.AccountGetRequest;
import com.programmers.heavenpay.account.dto.request.AccountUpdateRequest;
import com.programmers.heavenpay.account.dto.response.*;
import com.programmers.heavenpay.account.service.AccountService;
import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseDto> add(@Valid @RequestBody AccountCreateRequest request) {
        AccountCreateResponse response = accountService.create(
                request.getMemberId(),
                request.getTitle(),
                request.getDescription(),
                request.getNumber(),
                request.getFinanceId()
        );

        EntityModel<AccountCreateResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PUT.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.ACCOUNT_CREATE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("계좌 단건 조회")
    @GetMapping(value = "/{accountId}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> get(@PathVariable Long accountId, @Valid @RequestBody AccountGetRequest request) {
        AccountDetailResponse response = accountService.getOne(accountId, request.getMemberId());

        EntityModel<AccountDetailResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PUT.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.ACCOUNT_GET_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("계좌 전체 조회")
    @GetMapping(consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> getAll(@Valid @RequestBody AccountGetRequest request, Pageable pageable) {
        Page<AccountDetailAllResponse> response = accountService.getAll(request.getMemberId(), pageable);

        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        return responseConverter.toResponseEntity(
                ResponseMessage.ACCOUNT_GET_ALL_SUCCESS,
                response,
                link
        );
    }

    @ApiOperation("계좌 수정")
    @PutMapping(value = "/{accountId}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> edit(@PathVariable Long accountId, @Valid @RequestBody AccountUpdateRequest request) {
        AccountUpdateResponse response = accountService.update(request.getMemberId(), accountId, request.getTitle(), request.getDescription());

        EntityModel<AccountUpdateResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PUT.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.ACCOUNT_UPDATE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("계좌 삭제")
    @DeleteMapping(value = "/{accountId}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> remove(@PathVariable Long accountId, @Valid @RequestBody AccountDeleteRequest request) {
        AccountDeleteResponse response = accountService.delete(request.getMemberId(), accountId);

        EntityModel<AccountDeleteResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PUT.name()),
                getLinkToAddress().withSelfRel().withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.ACCOUNT_DELETE_SUCCESS,
                entityModel
        );
    }
}