package com.programmers.heavenpay.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.heavenpay.account.dto.request.AccountCreateRequest;
import com.programmers.heavenpay.account.dto.request.AccountGetRequest;
import com.programmers.heavenpay.account.dto.response.AccountCreateResponse;
import com.programmers.heavenpay.account.dto.response.AccountDetailResponse;
import com.programmers.heavenpay.account.service.AccountService;
import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
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

@WebMvcTest(AccountController.class)
class AccountControllerTest {
    private static final Long MEMBER_ID = 1L;
    private static final Long FINANCE_ID = 1L;
    private static final Long ACCOUNT_ID = 1L;
    private static final String ACCOUNT_TITLE = "계좌 별명";
    private static final String ACCOUNT_DESCRIPTION = "계좌 등록 설명";
    private static final String ACCOUNT_NUMBER = "12346642314";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @MockBean
    private ResponseConverter responseConverter;

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(AccountController.class);
    }

    private AccountCreateRequest accountCreateRequest = AccountCreateRequest.builder()
            .memberId(MEMBER_ID)
            .title(ACCOUNT_TITLE)
            .description(ACCOUNT_DESCRIPTION)
            .number(ACCOUNT_NUMBER)
            .financeId(FINANCE_ID)
            .build();

    private AccountCreateResponse accountCreateResponse = AccountCreateResponse.builder()
            .id(ACCOUNT_ID)
            .title(ACCOUNT_TITLE)
            .description(ACCOUNT_DESCRIPTION)
            .number(ACCOUNT_NUMBER)
            .createdAt(LocalDateTime.now())
            .modifiedAt(LocalDateTime.now())
            .build();

    private AccountDetailResponse accountDetailResponse = AccountDetailResponse.builder()
            .id(ACCOUNT_ID)
            .title(ACCOUNT_TITLE)
            .description(ACCOUNT_DESCRIPTION)
            .number(ACCOUNT_NUMBER)
            .createdAt(LocalDateTime.now())
            .modifiedAt(LocalDateTime.now())
            .build();

    private AccountGetRequest accountGetRequest = AccountGetRequest.builder()
            .memberId(ACCOUNT_ID)
            .build();

    @Test
    @DisplayName("계좌_생성")
    void createTest() throws Exception {
        // given
        EntityModel<AccountCreateResponse> entityModel = EntityModel.of(accountCreateResponse,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(ACCOUNT_ID).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name())
        );

        // when
        when(accountService.create(MEMBER_ID, ACCOUNT_TITLE, ACCOUNT_DESCRIPTION, ACCOUNT_NUMBER, FINANCE_ID))
                .thenReturn(accountCreateResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.ACCOUNT_CREATE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.ACCOUNT_CREATE_SUCCESS, entityModel)));

        // then
        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(accountCreateRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /*
    @Test
    void 계좌_조회() throws Exception {
        // given
        EntityModel<AccountDetailResponse> entityModel = EntityModel.of(accountDetailResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name())
        );

        // when
        when(accountService.getOne(ACCOUNT_ID, MEMBER_ID))
                .thenReturn(accountDetailResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.ACCOUNT_GET_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.ACCOUNT_GET_SUCCESS, entityModel)));

        // then
        mockMvc.perform(get("/api/v1/accounts/{accountId}", ACCOUNT_ID)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(accountGetRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }
     */
}