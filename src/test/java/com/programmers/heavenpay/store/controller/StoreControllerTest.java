package com.programmers.heavenpay.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.heavenpay.store.dto.request.StoreCreateRequest;
import com.programmers.heavenpay.store.service.StoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoreController.class)
class StoreControllerTest {
    @MockBean
    private StoreService storeService;

    @Autowired
    private MockMvc mockMvc;   //DispatcherServlet이 요청을 처리하는 과정을 확인

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("post 요청으로 store를 삽입할 수 있다.")
    void insertTest() throws Exception {
        // Given
        StoreCreateRequest request = StoreCreateRequest.builder()
                .vendorCode("108-15-84292")
                .type("식품")
                .name("신세게")
                .build();

        // When
        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/stores");
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        requestBuilder.content(objectMapper.writeValueAsString(request));
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated());   // TODO: test fail >> expected 201 but actual 415
    }
}