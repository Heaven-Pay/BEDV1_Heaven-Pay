package com.programmers.heavenpay.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.payment.dto.request.PaymentCreateRequest;
import com.programmers.heavenpay.payment.dto.request.PaymentDeleteRequest;
import com.programmers.heavenpay.payment.dto.response.PaymentCreateResponse;
import com.programmers.heavenpay.payment.dto.response.PaymentDeleteResponse;
import com.programmers.heavenpay.payment.service.PaymentService;
import io.swagger.models.HttpMethod;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PaymentController.class)
class PaymentControllerTest {
    private static final Long PAYMENT_ID = 1L;
    private static final Long POINT_WALLET_ID = 2L;
    private static final Long MEMBER_ID = 3L;
    private static final Long STORE_ID = 4L;

    private static final Integer PAYMENT_POINT = 2000;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private ResponseConverter responseConverter;

    private PaymentCreateRequest paymentCreateRequest = new PaymentCreateRequest(MEMBER_ID, STORE_ID, POINT_WALLET_ID, PAYMENT_POINT);

    private PaymentCreateResponse paymentCreateResponse = PaymentCreateResponse.builder()
            .id(PAYMENT_ID)
            .build();

    private PaymentDeleteRequest paymentDeleteRequest = new PaymentDeleteRequest(MEMBER_ID, STORE_ID, POINT_WALLET_ID);

    private PaymentDeleteResponse paymentDeleteResponse = PaymentDeleteResponse.builder()
            .id(PAYMENT_ID)
            .payPoint(PAYMENT_POINT)
            .build();

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(PaymentController.class);
    }

    @Test
    void addPayment() throws Exception {
        EntityModel<PaymentCreateResponse> entityModel = EntityModel.of(
                paymentCreateResponse,
                getLinkToAddress().withSelfRel().withTitle(HttpMethod.POST.name()),
                getLinkToAddress().slash(paymentCreateResponse.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(paymentCreateResponse.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(paymentCreateResponse.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        when(paymentService.create(MEMBER_ID, STORE_ID, POINT_WALLET_ID, PAYMENT_POINT))
                .thenReturn(paymentCreateResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.PAYMENT_INSERT_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.PAYMENT_INSERT_SUCCESS, entityModel)));

        // Then
        mockMvc.perform(post("/api/v1/payments")
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(paymentCreateRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void refund() throws Exception {
        EntityModel<PaymentDeleteResponse> entityModel = EntityModel.of(
                paymentDeleteResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(paymentDeleteResponse.getId()).withSelfRel().withType(HttpMethod.DELETE.name())
        );

        // when
        when(paymentService.delete(PAYMENT_ID, MEMBER_ID, STORE_ID, POINT_WALLET_ID))
                .thenReturn(paymentDeleteResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.PAYMENT_DELETE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.PAYMENT_DELETE_SUCCESS, entityModel)));

        // then
        mockMvc.perform(delete("/api/v1/payments/{paymentId}", PAYMENT_ID)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(paymentDeleteRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}