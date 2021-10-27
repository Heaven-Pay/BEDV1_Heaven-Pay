package com.programmers.heavenpay.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.heavenpay.store.dto.request.StoreCreateRequest;
import com.programmers.heavenpay.store.dto.request.StoreUpdateRequest;
import com.programmers.heavenpay.store.dto.response.StoreCreateResponse;
import com.programmers.heavenpay.store.entity.vo.StoreType;
import com.programmers.heavenpay.store.repository.StoreRepository;
import com.programmers.heavenpay.store.service.StoreService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(StoreController.class)  //@Controller, @ControllerAdvice 등만 스캔함(@Service나 @Repository는 불러오지 않는다.) -> 가벼운 테스팅 가능
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)  // TODO: 이걸 왜 전체를 다 로드해야되는지 모르겠음.
@AutoConfigureMockMvc  // TODO: 이건 왜 써야함
// SpringBootTest에서 서블릿 컨테이너를 모킹하고 AutoConfigureMockMvc에서 의존성을 주입 받음 (??)
class StoreControllerTest {
    @Autowired
    private MockMvc mockMvc;   //DispatcherServlet이 요청을 처리하는 과정을 확인

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreRepository storeRepository;

    String name = "파리바게뜨";
    String typeStr = "베이커리";
    String vendorCode = "108-15-84292";

    @AfterEach
    void tearDown() {
        storeRepository.deleteAll();
    }

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
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);  //Controller에서 HAL_JSON_VALUE 과 맞춰줌(헤더정보를 맞춰줌)
        requestBuilder.content(objectMapper.writeValueAsString(request)); // request를 String형태로 변환해서 requestbody에 삽입
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE); // Accept라는 헤더를 HAL_JSON_VALUE로 설정

        // Then
        mockMvc.perform(requestBuilder)  // 컨트롤러 실행
                .andDo(print())  // 결과 출력
                .andExpect(status().isCreated());   // 응답 결과 검증
    }

    @Test
    @DisplayName("delete 요청으로 store를 삭제할 수 있다.")
    void deleteTest() throws Exception {
        // Given
        StoreCreateResponse storeCreateResponse = storeService.create(name, typeStr, vendorCode);

        // When
        MockHttpServletRequestBuilder requestBuilder = delete("/api/v1/stores/{storeId}", storeCreateResponse.getId());
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("patch 요청으로 store를 수정할 수 있다.")
    void updateTest() throws Exception {
        // Given
        StoreCreateResponse storeCreateResponse = storeService.create(name, typeStr, vendorCode);

        String updateName = "BBQ";
        String updateTypeStr = "식품";
        String updateVendorCode = "111-11-11111";
        StoreUpdateRequest request = StoreUpdateRequest.builder()
                .name(updateName)
                .type(updateTypeStr)
                .vendorCode(updateVendorCode)
                .build();

        // When
        MockHttpServletRequestBuilder requestBuilder = patch("/api/v1/stores/{storeId}", storeCreateResponse.getId());
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.content(objectMapper.writeValueAsString(request));
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("get 요청으로 store 단건 조회할 수 있다.")
    void getOneTest() throws Exception {
        // Given
        storeService.create(name, typeStr, vendorCode);

        // When
        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/stores/{storeName}", name);
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("get 요청으로 모든 스토어를 조회할 수 있다.")
    void getAllTest() throws Exception {
        // Given
        storeService.create("store1", "식품", "111-11-11111");
        storeService.create("store2", "백화점", "111-11-11112");
        storeService.create("store3", "전자제품", "111-11-11113");

        // When
        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/stores");
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.param("page", String.valueOf(0));
        requestBuilder.param("size", String.valueOf(10));

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }
}