package com.programmers.heavenpay.finance.controller;

import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.finance.dto.request.FinanceCreateRequest;
import com.programmers.heavenpay.finance.dto.request.FinanceUpdateRequest;
import com.programmers.heavenpay.finance.dto.response.FinanceCreateResponse;
import com.programmers.heavenpay.finance.dto.response.FinanceDeleteResponse;
import com.programmers.heavenpay.finance.dto.response.FinanceDetailResponse;
import com.programmers.heavenpay.finance.dto.response.FinanceUpdateResponse;
import com.programmers.heavenpay.finance.service.FinanceService;
import com.programmers.heavenpay.common.dto.LinkType;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

// TODO :: 권한에 따른 API 호출 진행
@Api(tags = "Finance")
@RestController
@RequestMapping(value = "/api/v1/finances", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class FinanceController {
    private final FinanceService financeService;
    private final ResponseConverter responseConverter;

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(FinanceController.class);
    }

    @ApiOperation("금융 정보 생성")
    @PostMapping(consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> create(@Valid @RequestBody FinanceCreateRequest request) {
        FinanceCreateResponse response = financeService.create(request.getMemberId(), request.getFinanceName(), request.getFinanceType());

        EntityModel<FinanceCreateResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.FINANCE_CREATE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("금융 정보 단건 조회")
    @GetMapping(value = "/{financeId}")
    public ResponseEntity<ResponseDto> get(@PathVariable Long financeId) {
        FinanceDetailResponse response = financeService.getOne(financeId);

        EntityModel<FinanceDetailResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.FINANCE_READ_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("금융 정보 전체 조회")
    @GetMapping()
    public ResponseEntity<ResponseDto> getAll(Pageable pageable) {
        Page<FinanceDetailResponse> response = financeService.getAll(pageable);

        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        return responseConverter.toResponseEntity(
                HttpStatus.OK,
                ResponseMessage.FINANCE_READ_ALL_SUCCESS,
                response,
                link
        );
    }

    @ApiOperation("금융 정보 수정")
    @PatchMapping(value = "/{financeId}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> update(@PathVariable Long financeId, @Valid @RequestBody FinanceUpdateRequest request) {
        FinanceUpdateResponse response = financeService.update(request.getMemberId(), financeId, request.getFinanceName(), request.getFinanceType());

        EntityModel<FinanceUpdateResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.FINANCE_UPDATE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("금융 정보 삭제")
    @DeleteMapping(value = "/{financeId}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> delete(@PathVariable Long financeId) {
        FinanceDeleteResponse response = financeService.delete(financeId);

        EntityModel<FinanceDeleteResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withSelfRel().withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.FINANCE_DELETE_SUCCESS,
                entityModel
        );
    }
}