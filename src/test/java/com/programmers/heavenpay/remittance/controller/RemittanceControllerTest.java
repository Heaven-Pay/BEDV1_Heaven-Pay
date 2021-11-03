package com.programmers.heavenpay.remittance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.remittance.dto.request.RemittanceCreateRequest;
import com.programmers.heavenpay.remittance.dto.request.RemittanceGetRequest;
import com.programmers.heavenpay.remittance.dto.response.RemittanceCreateResponse;
import com.programmers.heavenpay.remittance.dto.response.RemittanceDetailAllResponse;
import com.programmers.heavenpay.remittance.dto.response.RemittanceGetResponse;
import com.programmers.heavenpay.remittance.service.RemittanceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RemittanceController.class)
class RemittanceControllerTest {
    private static final Long REMITTANCE_ID = 1L;
    private static final Long MEMBER_ID = 1L;
    private static final Long FINANCE_ID = 1L;
    private static final Long ACCOUNT_ID = 1L;
    private static final String FINANCE_NAME = "신한";
    private static final String NAME = "김동건";
    private static final String MEMBER_NAME = "황인준";
    private static final String NUMBER = "524586456349654";
    private static final Integer MONEY = 1000;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RemittanceService remittanceService;

    @MockBean
    private ResponseConverter responseConverter;

    @MockBean
    private Pageable pageable;

    @MockBean
    Page<RemittanceDetailAllResponse> remittanceDetailAllResponses;

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(RemittanceController.class);
    }

    private RemittanceCreateRequest remittanceCreateRequest = new RemittanceCreateRequest(
            MEMBER_ID,
            ACCOUNT_ID,
            FINANCE_ID,
            NAME,
            NUMBER,
            MONEY
    );

    private RemittanceCreateResponse remittanceCreateResponse = RemittanceCreateResponse.builder()
            .remittanceId(REMITTANCE_ID)
            .memberName(MEMBER_NAME)
            .financeName(FINANCE_NAME)
            .remittanceName(NAME)
            .remittanceNumber(NUMBER)
            .remittanceMoney(MONEY)
            .createdAt(LocalDateTime.now())
            .modifiedAt(LocalDateTime.now())
            .build();

    private RemittanceGetRequest remittanceGetRequest = new RemittanceGetRequest(MEMBER_ID);

    private RemittanceGetResponse remittanceGetResponse = RemittanceGetResponse.builder()
            .remittanceId(REMITTANCE_ID)
            .memberName(MEMBER_NAME)
            .financeName(FINANCE_NAME)
            .remittanceName(NAME)
            .remittanceNumber(NUMBER)
            .remittanceMoney(MONEY)
            .modifiedAt(LocalDateTime.now())
            .build();

    @Test
    void 송금하기() throws Exception {
        // given
        EntityModel<RemittanceCreateResponse> entityModel = EntityModel.of(remittanceCreateResponse,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(remittanceCreateResponse.getRemittanceId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name())
        );

        // when
        when(remittanceService.create(MEMBER_ID, ACCOUNT_ID, FINANCE_ID, NAME, NUMBER, MONEY))
                .thenReturn(remittanceCreateResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.REMITTANCE_CREATE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.REMITTANCE_CREATE_SUCCESS, entityModel)));

        // then
        mockMvc.perform(post("/api/v1/remittances")
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(remittanceCreateRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 송금_단건_조회() throws Exception {
        // given
        EntityModel<RemittanceGetResponse> entityModel = EntityModel.of(remittanceGetResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name())
        );

        // when
        when(remittanceService.get(MEMBER_ID, REMITTANCE_ID))
                .thenReturn(remittanceGetResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.REMITTANCE_READ_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.REMITTANCE_READ_SUCCESS, entityModel)));

        // then
        mockMvc.perform(get("/api/v1/remittances/{remittanceId}", REMITTANCE_ID)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(remittanceGetRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 송금_전체_조회() throws Exception {
        // given
        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        // when
        when(remittanceService.getAll(REMITTANCE_ID, pageable)).thenReturn(remittanceDetailAllResponses);
        when(responseConverter.toResponseEntity(ResponseMessage.REMITTANCE_READ_SUCCESS, remittanceDetailAllResponses, link))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.REMITTANCE_READ_SUCCESS, remittanceDetailAllResponses, link)));

        // then
        mockMvc.perform(get("/api/v1/remittances")
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(remittanceGetRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}