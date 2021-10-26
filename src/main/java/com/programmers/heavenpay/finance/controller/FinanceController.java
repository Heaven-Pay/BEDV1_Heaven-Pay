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
import com.programmers.heavenpay.utill.HateoasMethodType;
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
@RestController
@RequestMapping(value = "/api/v1/finances", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class FinanceController {
    private final FinanceService financeService;
    private final ResponseConverter responseConverter;

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(FinanceController.class);
    }

    @PostMapping(consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> create(@Valid @RequestBody FinanceCreateRequest request) {
        FinanceCreateResponse response = financeService.create(request.getMemberId(), request.getFinanceName(), request.getFinanceType());

        EntityModel<FinanceCreateResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(HateoasMethodType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(HateoasMethodType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(HateoasMethodType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(HateoasMethodType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.FINANCE_CREATE_SUCCESS,
                entityModel
        );
    }

    @GetMapping(value = "/{financeId}")
    public ResponseEntity<ResponseDto> read(@PathVariable Long financeId) {
        FinanceDetailResponse response = financeService.read(financeId);

        EntityModel<FinanceDetailResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().withRel(HateoasMethodType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(HateoasMethodType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(HateoasMethodType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(HateoasMethodType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.FINANCE_READ_SUCCESS,
                entityModel
        );
    }

    @GetMapping()
    public ResponseEntity<ResponseDto> read(Pageable pageable) {
        Page<FinanceDetailResponse> response = financeService.read(pageable);

        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        return responseConverter.toResponseEntity(
                HttpStatus.OK,
                ResponseMessage.FINANCE_READ_ALL_SUCCESS,
                response,
                link
        );
    }

    @PatchMapping(value = "/{financeId}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> update(@PathVariable Long financeId, @Valid @RequestBody FinanceUpdateRequest request) {
        FinanceUpdateResponse response = financeService.update(request.getMemberId(), financeId, request.getFinanceName(), request.getFinanceType());

        EntityModel<FinanceUpdateResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().withRel(HateoasMethodType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(HateoasMethodType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(HateoasMethodType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(HateoasMethodType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.FINANCE_UPDATE_SUCCESS,
                entityModel
        );
    }

    @DeleteMapping(value = "/{financeId}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> delete(@PathVariable Long financeId) {
        FinanceDeleteResponse response = financeService.delete(financeId);

        EntityModel<FinanceDeleteResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().withRel(HateoasMethodType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(HateoasMethodType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(HateoasMethodType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(HateoasMethodType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().withSelfRel().withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.FINANCE_DELETE_SUCCESS,
                entityModel
        );
    }
}