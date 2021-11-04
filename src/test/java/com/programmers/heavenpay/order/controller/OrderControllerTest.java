package com.programmers.heavenpay.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(OrderController.class)
class OrderControllerTest {
    private Long memberId = 1L;
    private Long productId = 2L;
    private int quantity = 3;
    private Long orderId = 3L;
    private String status = "결제완료";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ResponseConverter responseConverter;

    @MockBean
    private Pageable pageable;

    // ### dto define area ### //
    @Mock
    private Page<OrderInfoResponse> orderInfoResponsePage;

    private OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
            .memberId(memberId)
            .produtId(productId)
            .quantity(quantity)
            .build();

    private OrderCreateResponse orderCreateResponse = OrderCreateResponse.builder()
            .createdAt(LocalDateTime.now())
            .id(orderId)
            .build();

    private OrderUpdateRequest orderUpdateRequest = OrderUpdateRequest.builder()
            .quantity(quantity)
            .status(status)
            .build();

    private OrderUpdateResponse orderUpdateResponse = OrderUpdateResponse.builder()
            .id(orderId)
            .build();

    private OrderInfoResponse orderInfoResponse = OrderInfoResponse.builder()
            .mdifiedAt(LocalDateTime.now())
            .createdAt(LocalDateTime.now())
            .id(orderId)
            .status(status)
            .quantity(quantity)
            .productId(productId)
            .build();
    // ### end of dto define area ### //

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(OrderController.class);
    }

    @Test
    void 주문_생성_성공_테스트() throws Exception {
        //given
        EntityModel<OrderCreateResponse> entityModel = EntityModel.of(
                orderCreateResponse,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(orderCreateResponse.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(orderCreateResponse.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(orderCreateResponse.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        //when
        when(orderService.create(quantity, memberId, productId))
                .thenReturn(orderCreateResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.ORDER_INSERT_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.ORDER_INSERT_SUCCESS, entityModel)));

        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/orders");
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.content(objectMapper.writeValueAsString(orderCreateRequest));
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 주문_수정_성공_테스트() throws Exception {
        //given
        EntityModel<OrderUpdateResponse> entityModel = EntityModel.of(
                orderUpdateResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(orderUpdateResponse.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(orderUpdateResponse.getId()).withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(orderUpdateResponse.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        //when
        when(orderService.update(orderId, quantity, status))
                .thenReturn(orderUpdateResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.ORDER_UPDATE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.ORDER_UPDATE_SUCCESS, entityModel)));

        MockHttpServletRequestBuilder requestBuilder = patch("/api/v1/orders/{orderId}", orderId);
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.content(objectMapper.writeValueAsString(orderUpdateRequest));
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 주문_단건_조회_성공_테스트() throws Exception {
        //given
        EntityModel<OrderInfoResponse> entityModel = EntityModel.of(
                orderInfoResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(orderInfoResponse.getId()).withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(orderInfoResponse.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(orderInfoResponse.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        //when
        when(orderService.findById(orderId))
                .thenReturn(orderInfoResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.ORDER_SEARCH_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.ORDER_SEARCH_SUCCESS, entityModel)));

        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/orders/{orderId}", orderId);
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 특정사용자의_모든_주문_조회_성공_테스트() throws Exception {
        //given
        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        //when
        when(orderService.findAllByPages(memberId, pageable))
                .thenReturn(orderInfoResponsePage);
        when(responseConverter.toResponseEntity(ResponseMessage.ORDER_SEARCH_SUCCESS, orderInfoResponsePage, link))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.ORDER_SEARCH_SUCCESS, orderInfoResponsePage, link)));


        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/members/{memberId}/orders", memberId);
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }
}