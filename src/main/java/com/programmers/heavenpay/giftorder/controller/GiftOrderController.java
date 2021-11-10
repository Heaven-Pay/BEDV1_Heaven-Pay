package com.programmers.heavenpay.giftorder.controller;

import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.giftorder.dto.request.GiftOrderCreateRequest;
import com.programmers.heavenpay.giftorder.dto.request.GiftOrderUpdateRequest;
import com.programmers.heavenpay.giftorder.dto.response.GiftOrderCreateResponse;
import com.programmers.heavenpay.giftorder.dto.response.GiftOrderInfoResponse;
import com.programmers.heavenpay.giftorder.dto.response.GiftOrderUpdateResponse;
import com.programmers.heavenpay.giftorder.service.GiftOrderService;
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

@RestController
@RequestMapping(value = "/api/v1", produces = MediaTypes.HAL_JSON_VALUE)
@Api(tags = "Gift Order")
public class GiftOrderController {
    private final GiftOrderService giftOrderService;
    private final ResponseConverter responseConverter;

    public GiftOrderController(GiftOrderService giftOrderService, ResponseConverter responseConverter) {
        this.giftOrderService = giftOrderService;
        this.responseConverter = responseConverter;
    }

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(GiftOrderController.class);
    }

    @ApiOperation("Gift Order 신규 추가, 성공시 생성된 Gift Order ID 반환")
    @PostMapping(value = "/gift_orders", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> insert(@Valid @RequestBody GiftOrderCreateRequest request) {
        GiftOrderCreateResponse response = giftOrderService.create(request.getQuantity(), request.getMemberId(), request.getTargetMemberId(), request.getProdutId());

        EntityModel<GiftOrderCreateResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.GIFT_ORDER_INSERT_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("Order 단건 수정, 성공시 수정된 Order 정보 반환")
    @PatchMapping(value = "/gift_orders/{giftOrderId}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> update(@PathVariable Long giftOrderId, @Valid @RequestBody GiftOrderUpdateRequest request) {
        GiftOrderUpdateResponse response = giftOrderService.update(giftOrderId, request.getQuantity(), request.getStatus());

        EntityModel<GiftOrderUpdateResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.GIFT_ORDER_UPDATE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("Order 단건 조회, 성공시 Order 정보 반환")
    @GetMapping(value = "/gift_orders/{giftOrderId}")
    public ResponseEntity<ResponseDto> getOne(@PathVariable Long giftOrderId) {
        GiftOrderInfoResponse response = giftOrderService.findById(giftOrderId);

        EntityModel<GiftOrderInfoResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.GIFT_ORDER_SEARCH_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("특정 사용자의 모든 Order 조회, 성공시 Order Page 반환")
    @GetMapping(value = "/gift_orders")
    public ResponseEntity<ResponseDto> getAll(Pageable pageable, @RequestParam Long memberId) {
        Page<GiftOrderInfoResponse> responses = giftOrderService.findAllByPages(memberId, pageable);

        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        return responseConverter.toResponseEntity(
                ResponseMessage.GIFT_ORDER_SEARCH_SUCCESS,
                responses,
                link
        );
    }
}
