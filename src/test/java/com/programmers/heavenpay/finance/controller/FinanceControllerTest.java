package com.programmers.heavenpay.finance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.heavenpay.finance.dto.request.FinanceCreateRequest;
import com.programmers.heavenpay.finance.dto.request.FinanceUpdateRequest;
import com.programmers.heavenpay.finance.service.FinanceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 성공 실패 꼭꼭!!
// 모키토를 사용해서 Service와 Repository를 막아주기
//@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class FinanceControllerTest {
    @Autowired
    FinanceService financeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    Long memberId = 1L;
    Long financeId = 1L;
    String name = "국민은행";
    String type = "은행";
    String updatedName = "신한은행";
    String updatedType = "증권";

    @Test
    @DisplayName("금융_정보_생성")
    void createTest() throws Exception {
        // given
        FinanceCreateRequest request = FinanceCreateRequest.builder()
                .memberId(1L)
                .financeName("국민은행")
                .financeType("증권")
                .build();

        // when
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/api/v1/finances");
        mockHttpServletRequestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        mockHttpServletRequestBuilder.content(objectMapper.writeValueAsString(request));
        mockHttpServletRequestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("금융_정보_단건_조회")
    void getOneTest() throws Exception {
        // given
        financeService.create(memberId, name, type);

        // when
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get("/api/v1/finances/{financeId}", financeId);
        mockHttpServletRequestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("금융_정보_전체_조회")
    void getAllTest() throws Exception {
        // given
        financeService.create(memberId, name, type);

        // when
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get("/api/v1/finances");
        mockHttpServletRequestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("금융_정보_수정")
    void updateTest() throws Exception {
        // given
        financeService.create(memberId, name, type);
        FinanceUpdateRequest request = FinanceUpdateRequest.builder()
                .memberId(memberId)
                .financeName(updatedName)
                .financeType(updatedType)
                .build();

        // when
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = patch("/api/v1/finances/{financeId}", financeId);
        mockHttpServletRequestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        mockHttpServletRequestBuilder.content(objectMapper.writeValueAsString(request));
        mockHttpServletRequestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("금융_정보_삭제")
    void deleteTest() throws Exception {
        // given
        financeService.create(memberId, name, type);

        // when
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = delete("/api/v1/finances/{financeId}", financeId);
        mockHttpServletRequestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        mockHttpServletRequestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andDo(print());
    }
}