package com.programmers.heavenpay.store.controller;

import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.store.dto.request.StoreCreateRequest;
import com.programmers.heavenpay.store.dto.request.StoreUpdateRequest;
import com.programmers.heavenpay.store.dto.response.StoreCreateResponse;
import com.programmers.heavenpay.store.dto.response.StoreDeleteResponse;
import com.programmers.heavenpay.store.dto.response.StoreInfoResponse;
import com.programmers.heavenpay.store.dto.response.StoreUpdateResponse;
import com.programmers.heavenpay.store.service.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/v1/stores", produces = MediaTypes.HAL_JSON_VALUE)
@Api(tags = "Store")
public class StoreController {
    private final StoreService storeService;
    private final ResponseConverter responseConverter;

    public StoreController(StoreService storeService, ResponseConverter responseConverter) {
        this.storeService = storeService;
        this.responseConverter = responseConverter;
    }

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(StoreController.class);
    }

    @ApiOperation("Store 신규 추가, 성공시 생성된 Store ID 반환")
    @PostMapping(consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> insert(@Valid @RequestBody StoreCreateRequest request) {
        StoreCreateResponse response = storeService.create(request.getName(), request.getType(), request.getVendorCode());

        EntityModel<StoreCreateResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.STORE_INSERT_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("Store 단건 삭제, 성공시 삭제된 Store ID 반환")
    @DeleteMapping(value = "/{storeId}")
    public ResponseEntity<ResponseDto> delete(@Valid @PathVariable Long storeId) {
        StoreDeleteResponse response = storeService.delete(storeId);

        EntityModel<StoreDeleteResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.STORE_DELETE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("Store 단건 수정, 성공시 수정된 Store 정보 반환")
    @PatchMapping(value = "/{storeId}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> update(@PathVariable Long storeId, @Valid @RequestBody StoreUpdateRequest request) {
        StoreUpdateResponse response = storeService.update(storeId, request.getName(), request.getType(), request.getVendorCode());

        EntityModel<StoreUpdateResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.STORE_UPDATE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("Store 단건 조회, 성공시 Store 정보 반환")
    @GetMapping(value = "/{storeName}")
    public ResponseEntity<ResponseDto> getOne(@PathVariable String storeName) {
        StoreInfoResponse response = storeService.findByName(storeName);

        EntityModel<StoreInfoResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.STORE_SEARCH_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("모든 Store 조회, 성공시 Store Page 반환")
    @GetMapping()
    public ResponseEntity<ResponseDto> getAll(Pageable pageable) {
        Page<StoreInfoResponse> responses = storeService.findAllByPages(pageable);

        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        return responseConverter.toResponseEntity(
                ResponseMessage.STORE_SEARCH_SUCCESS,
                responses,
                link
        );
    }
}
