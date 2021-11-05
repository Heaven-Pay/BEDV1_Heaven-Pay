package com.programmers.heavenpay.giftorder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
@WebMvcTest(GiftOrderController.class)
class GiftOrderControllerTest {
    private Long memberId = 1L;
    private Long targetMemberId = 4L;
    private Long productId = 2L;
    private int quantity = 3;
    private Long giftOrderId = 3L;
    private String status = "결제완료";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GiftOrderService giftOrderService;

    @MockBean
    private ResponseConverter responseConverter;

    @MockBean
    private Pageable pageable;

    // ### dto define area ### //
    @Mock
    private Page<GiftOrderInfoResponse> orderInfoResponsePage;

    private GiftOrderCreateRequest giftOrderCreateRequest = GiftOrderCreateRequest.builder()
            .memberId(memberId)
            .produtId(productId)
            .targetMemberId(targetMemberId)
            .quantity(quantity)
            .build();

    private GiftOrderCreateResponse giftOrderCreateResponse = GiftOrderCreateResponse.builder()
            .createdAt(LocalDateTime.now())
            .id(giftOrderId)
            .build();

    private GiftOrderUpdateRequest giftOrderUpdateRequest = GiftOrderUpdateRequest.builder()
            .quantity(quantity)
            .status(status)
            .build();

    private GiftOrderUpdateResponse giftOrderUpdateResponse = GiftOrderUpdateResponse.builder()
            .id(giftOrderId)
            .build();

    private GiftOrderInfoResponse giftOrderInfoResponse = GiftOrderInfoResponse.builder()
            .mdifiedAt(LocalDateTime.now())
            .createdAt(LocalDateTime.now())
            .id(giftOrderId)
            .status(status)
            .quantity(quantity)
            .productId(productId)
            .build();
    // ### end of dto define area ### //

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(GiftOrderController.class);
    }

    @Test
    void 주문_생성_성공_테스트() throws Exception {
        //given
        EntityModel<GiftOrderCreateResponse> entityModel = EntityModel.of(
                giftOrderCreateResponse,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(giftOrderCreateResponse.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(giftOrderCreateResponse.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(giftOrderCreateResponse.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        //when
        when(giftOrderService.create(quantity, memberId, targetMemberId, productId))
                .thenReturn(giftOrderCreateResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.GIFT_ORDER_INSERT_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.GIFT_ORDER_INSERT_SUCCESS, entityModel)));

        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/gift_orders");
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.content(objectMapper.writeValueAsString(giftOrderCreateRequest));
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 주문_수정_성공_테스트() throws Exception {
        //given
        EntityModel<GiftOrderUpdateResponse> entityModel = EntityModel.of(
                giftOrderUpdateResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(giftOrderUpdateResponse.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(giftOrderUpdateResponse.getId()).withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(giftOrderUpdateResponse.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        //when
        when(giftOrderService.update(giftOrderId, quantity, status))
                .thenReturn(giftOrderUpdateResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.GIFT_ORDER_UPDATE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.GIFT_ORDER_UPDATE_SUCCESS, entityModel)));

        MockHttpServletRequestBuilder requestBuilder = patch("/api/v1/gift_orders/{orderId}", giftOrderId);
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.content(objectMapper.writeValueAsString(giftOrderUpdateRequest));
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 주문_단건_조회_성공_테스트() throws Exception {
        //given
        EntityModel<GiftOrderInfoResponse> entityModel = EntityModel.of(
                giftOrderInfoResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(giftOrderInfoResponse.getId()).withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(giftOrderInfoResponse.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(giftOrderInfoResponse.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        //when
        when(giftOrderService.findById(giftOrderId))
                .thenReturn(giftOrderInfoResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.GIFT_ORDER_SEARCH_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.GIFT_ORDER_SEARCH_SUCCESS, entityModel)));

        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/gift_orders/{orderId}", giftOrderId);
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
        when(giftOrderService.findAllByPages(memberId, pageable))
                .thenReturn(orderInfoResponsePage);
        when(responseConverter.toResponseEntity(ResponseMessage.GIFT_ORDER_SEARCH_SUCCESS, orderInfoResponsePage, link))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.GIFT_ORDER_SEARCH_SUCCESS, orderInfoResponsePage, link)));


        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/gift_orders?memberId={memberId}", memberId);
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }
}