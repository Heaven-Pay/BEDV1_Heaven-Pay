package com.programmers.heavenpay.order.controller;

import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.order.dto.request.OrderCreateRequest;
import com.programmers.heavenpay.order.dto.request.OrderUpdateRequest;
import com.programmers.heavenpay.order.dto.response.OrderCreateResponse;
import com.programmers.heavenpay.order.dto.response.OrderInfoResponse;
import com.programmers.heavenpay.order.dto.response.OrderUpdateResponse;
import com.programmers.heavenpay.order.service.OrderService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1", produces = MediaTypes.HAL_JSON_VALUE)
@Api(value = "Order API")
public class OrderController {
    private final OrderService orderService;
    private final ResponseConverter responseConverter;

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(OrderController.class);
    }

    @ApiOperation("Order 신규 추가, 성공시 생성된 Order ID 반환")
    @PostMapping(value = "/orders", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> insert(@Valid @RequestBody OrderCreateRequest request) {
        OrderCreateResponse response = orderService.create(request.getQuantity(), request.getMemberId(), request.getProdutId());

        EntityModel<OrderCreateResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.ORDER_INSERT_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("Order 단건 수정, 성공시 수정된 Order 정보 반환")
    @PatchMapping(value = "/orders/{orderId}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> update(@PathVariable Long orderId, @Valid @RequestBody OrderUpdateRequest request) {
        OrderUpdateResponse response = orderService.update(orderId, request.getQuantity(), request.getStatus());

        EntityModel<OrderUpdateResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.ORDER_UPDATE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("Order 단건 조회, 성공시 Order 정보 반환")
    @GetMapping(value = "/orders/{orderId}")
    public ResponseEntity<ResponseDto> getOne(@PathVariable Long orderId) {
        OrderInfoResponse response = orderService.findById(orderId);

        EntityModel<OrderInfoResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.ORDER_SEARCH_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("특정 사용자의 모든 Order 조회, 성공시 Order Page 반환")
    @GetMapping(value = "/members/{memberId}/orders")
    public ResponseEntity<ResponseDto> getAll(Pageable pageable, @PathVariable Long memberId) {
        Page<OrderInfoResponse> responses = orderService.findAllByPages(memberId, pageable);

        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        return responseConverter.toResponseEntity(
                HttpStatus.OK,
                ResponseMessage.ORDER_SEARCH_SUCCESS,
                responses,
                link
        );
    }
}
