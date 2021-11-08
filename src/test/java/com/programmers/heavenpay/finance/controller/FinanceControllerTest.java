package com.programmers.heavenpay.finance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.heavenpay.account.controller.AccountController;
import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.finance.dto.request.FinanceCreateRequest;
import com.programmers.heavenpay.finance.dto.request.FinanceUpdateRequest;
import com.programmers.heavenpay.finance.dto.response.FinanceCreateResponse;
import com.programmers.heavenpay.finance.dto.response.FinanceDeleteResponse;
import com.programmers.heavenpay.finance.dto.response.FinanceDetailResponse;
import com.programmers.heavenpay.finance.dto.response.FinanceUpdateResponse;
import com.programmers.heavenpay.finance.service.FinanceService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(FinanceController.class)
class FinanceControllerTest {
    private static final Long MEMBER_ID = 1L;
    private static final Long FINANCE_ID = 1L;
    private static final String FINANCE_NAME = "신한";
    private static final String FINANCE_TYPE = "은행";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FinanceService financeService;

    @MockBean
    private ResponseConverter responseConverter;

    @MockBean
    private Pageable pageable;

    @MockBean
    Page<FinanceDetailResponse> financeDetailAllResponse;

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(AccountController.class);
    }

    private FinanceCreateRequest financeCreateRequest = new FinanceCreateRequest(
            MEMBER_ID,
            FINANCE_NAME,
            FINANCE_TYPE
    );

    private FinanceCreateResponse financeCreateResponse = FinanceCreateResponse.builder()
            .id(FINANCE_ID)
            .financeName(FINANCE_NAME)
            .financeType(FINANCE_TYPE)
            .createdAt(LocalDateTime.now())
            .modifiedAt(LocalDateTime.now())
            .build();

    private FinanceDetailResponse financeDetailResponse = FinanceDetailResponse.builder()
            .id(FINANCE_ID)
            .financeName(FINANCE_NAME)
            .financeType(FINANCE_TYPE)
            .createdAt(LocalDateTime.now())
            .modifiedAt(LocalDateTime.now())
            .build();

    private FinanceUpdateRequest financeUpdateRequest = new FinanceUpdateRequest(
            MEMBER_ID,
            FINANCE_NAME,
            FINANCE_TYPE
    );

    private FinanceUpdateResponse financeUpdateResponse = FinanceUpdateResponse.builder()
            .id(FINANCE_ID)
            .financeName(FINANCE_NAME)
            .financeType(FINANCE_TYPE)
            .createdAt(LocalDateTime.now())
            .modifiedAt(LocalDateTime.now())
            .build();

    private FinanceDeleteResponse financeDeleteResponse = FinanceDeleteResponse.builder()
            .id(FINANCE_ID)
            .financeName(FINANCE_NAME)
            .financeType(FINANCE_TYPE)
            .createdAt(LocalDateTime.now())
            .modifiedAt(LocalDateTime.now())
            .build();

    @Test
    void 금융정보_생성() throws Exception {
        // given
        EntityModel<FinanceCreateResponse> entityModel = EntityModel.of(financeCreateResponse,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(financeCreateResponse.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(financeCreateResponse.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(financeCreateResponse.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        // when
        when(financeService.create(MEMBER_ID, FINANCE_NAME, FINANCE_TYPE))
                .thenReturn(financeCreateResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.FINANCE_CREATE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.FINANCE_CREATE_SUCCESS, entityModel)));

        // then
        mockMvc.perform(post("/api/v1/finances")
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(financeCreateRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 금융정보_단건_조회() throws Exception {
        // given
        EntityModel<FinanceDetailResponse> entityModel = EntityModel.of(financeDetailResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(financeDetailResponse.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(financeDetailResponse.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        // when
        when(financeService.find(FINANCE_ID))
                .thenReturn(financeDetailResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.FINANCE_READ_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.FINANCE_READ_SUCCESS, entityModel)));


        // then
        mockMvc.perform(get("/api/v1/finances/{financeId}", FINANCE_ID)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 금융_정보_전체_조회() throws Exception {
        // given
        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        // when
        when(financeService.findAll(pageable)).thenReturn(financeDetailAllResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.FINANCE_READ_ALL_SUCCESS, financeDetailAllResponse, link))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.FINANCE_READ_ALL_SUCCESS, financeDetailAllResponse, link)));

        // then
        mockMvc.perform(get("/api/v1/finances")
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 금융정보_수정() throws Exception {
        // given
        EntityModel<FinanceUpdateResponse> entityModel = EntityModel.of(financeUpdateResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(financeUpdateResponse.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(financeUpdateResponse.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        // when
        when(financeService.update(MEMBER_ID, FINANCE_ID, FINANCE_NAME, FINANCE_TYPE))
                .thenReturn(financeUpdateResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.FINANCE_UPDATE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.FINANCE_UPDATE_SUCCESS, entityModel)));

        // then
        mockMvc.perform(patch("/api/v1/finances/{financeId}", FINANCE_ID)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(financeUpdateRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 금융정보_삭제() throws Exception {
        // given
        EntityModel<FinanceDeleteResponse> entityModel = EntityModel.of(financeDeleteResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(financeDeleteResponse.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withSelfRel().withType(HttpMethod.DELETE.name())
        );

        // when
        when(financeService.delete(MEMBER_ID))
                .thenReturn(financeDeleteResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.FINANCE_DELETE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.FINANCE_DELETE_SUCCESS, entityModel)));

        // then
        mockMvc.perform(delete("/api/v1/finances/{financeId}", FINANCE_ID)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print());
    }
}