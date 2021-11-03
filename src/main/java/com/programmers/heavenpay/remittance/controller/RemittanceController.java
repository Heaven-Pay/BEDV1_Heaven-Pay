package com.programmers.heavenpay.remittance.controller;

import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.remittance.dto.request.RemittanceCreateRequest;
import com.programmers.heavenpay.remittance.dto.response.RemittanceCreateResponse;
import com.programmers.heavenpay.remittance.dto.request.RemittanceGetRequest;
import com.programmers.heavenpay.remittance.dto.response.RemittanceDetailAllResponse;
import com.programmers.heavenpay.remittance.dto.response.RemittanceGetResponse;
import com.programmers.heavenpay.remittance.service.RemittanceService;
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

@Api(tags = "Remittance")
@RestController
@RequestMapping(value = "/api/v1/remittances", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class RemittanceController {
    private final RemittanceService remittanceService;
    private final ResponseConverter responseConverter;

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(RemittanceController.class);
    }

    @ApiOperation("송금하기")
    @PostMapping(consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> add(@Valid @RequestBody RemittanceCreateRequest request) {
        RemittanceCreateResponse response = remittanceService.create(
                request.getMemberId(),
                request.getAccountId(),
                request.getFinanceId(),
                request.getName(),
                request.getNumber(),
                request.getMoney()
        );

        EntityModel<RemittanceCreateResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getRemittanceId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.REMITTANCE_CREATE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("송금 단건 조회")
    @GetMapping(value = "/{remittanceId}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> get(@PathVariable Long remittanceId, @Valid @RequestBody RemittanceGetRequest request) {
        RemittanceGetResponse response = remittanceService.get(request.getMemberId(), remittanceId);

        EntityModel<RemittanceGetResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.REMITTANCE_READ_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("송금 전체 조회")
    @GetMapping(consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> getAll(@Valid @RequestBody RemittanceGetRequest request, Pageable pageable) {
        Page<RemittanceDetailAllResponse> response = remittanceService.getAll(request.getMemberId(), pageable);

        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        return responseConverter.toResponseEntity(
                ResponseMessage.REMITTANCE_READ_SUCCESS,
                response,
                link
        );
    }
}
