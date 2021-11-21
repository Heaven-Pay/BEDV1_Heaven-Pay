package com.programmers.heavenpay.pointHistory.controller;

import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.pointHistory.dto.request.PointHistoryCreateRequest;
import com.programmers.heavenpay.pointHistory.dto.request.PointHistoryDeleteRequest;
import com.programmers.heavenpay.pointHistory.dto.request.PointHistoryGetOneRequest;
import com.programmers.heavenpay.pointHistory.dto.request.PointHistoryUpdateRequest;
import com.programmers.heavenpay.pointHistory.dto.response.PointHistoryCreateResponse;
import com.programmers.heavenpay.pointHistory.dto.response.PointHistoryDeleteResponse;
import com.programmers.heavenpay.pointHistory.dto.response.PointHistoryGetOneResponse;
import com.programmers.heavenpay.pointHistory.dto.response.PointHistoryUpdateResponse;
import com.programmers.heavenpay.pointHistory.service.PointHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(tags = "PointHistory")
@RestController
@RequestMapping(value = "/api/v1/point_histories", produces = MediaTypes.HAL_JSON_VALUE)
public class PointHistoryController {
    private final PointHistoryService pointHistoryService;
    private final ResponseConverter responseConverter;

    public PointHistoryController(PointHistoryService pointHistoryService, ResponseConverter responseConverter) {
        this.pointHistoryService = pointHistoryService;
        this.responseConverter = responseConverter;
    }

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(PointHistoryController.class);
    }

    @ApiOperation("포인트 결제 내역 추가, 성공시 생성된 PointHistory ID 반환")
    @PostMapping(consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> insert(@Valid @RequestBody PointHistoryCreateRequest request) {
        PointHistoryCreateResponse response = pointHistoryService.create(
                request.getMemberId(),
                request.getUsedApp(),
                request.getDescription(),
                request.getUsePoint()
        );

        EntityModel<PointHistoryCreateResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withSelfRel().withTitle(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.POINT_HISTORY_CREATE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("포인트 결제 내역의 설명 수정, 성공시 수정된 포인트 결제 내역 데이터 반환")
    @PutMapping(value = "/{pointHistoryId}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> editDescriptionAndUsePoint(@PathVariable Long pointHistoryId, @Valid @RequestBody PointHistoryUpdateRequest request) {
        PointHistoryUpdateResponse response = pointHistoryService.update(
                pointHistoryId,
                request.getMemberId(),
                request.getDescription(),
                request.getUsePoint()
        );

        EntityModel<PointHistoryUpdateResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.POINT_HISTORY_UPDATE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("포인트 결제 내역 단건 삭제, 성공시 삭제된 포인트 결제 내역 데이터 반환")
    @DeleteMapping(value = "/{pointHistoryId}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> remove(@Valid @PathVariable Long pointHistoryId, @Valid @RequestBody PointHistoryDeleteRequest request) {
        PointHistoryDeleteResponse response = pointHistoryService.delete(pointHistoryId, request.getMemberId());

        EntityModel<PointHistoryDeleteResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.POINT_HISTORY_DELETE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("포인트 결제 내역 단건 조회, 성공시 조회한 한 개의 포인트 결제 내역 데이터 반환")
    @GetMapping(value = "/{pointHistoryId}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> readSingleData(@PathVariable Long pointHistoryId, @RequestBody PointHistoryGetOneRequest request) {
        PointHistoryGetOneResponse response = pointHistoryService.getOne(pointHistoryId, request.getMemberId());

        EntityModel<PointHistoryGetOneResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.POINT_HISTORY_READ_ONE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("포인트 결제 내역 고객의 모든 데이터 조회, 성공시 조회한 한 개의 포인트 결제 내역 데이터 반환")
    @GetMapping(consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> readPage(@RequestBody PointHistoryGetOneRequest request, Pageable pageable) {
        Page<PointHistoryGetOneResponse> response = pointHistoryService.getAll(request.getMemberId(), pageable);

        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        return responseConverter.toResponseEntity(
                ResponseMessage.POINT_HISTORY_READ_ALL_SUCCESS,
                response,
                link
        );
    }
}
