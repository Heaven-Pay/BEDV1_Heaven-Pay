package com.programmers.heavenpay.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.store.dto.request.StoreCreateRequest;
import com.programmers.heavenpay.store.dto.request.StoreUpdateRequest;
import com.programmers.heavenpay.store.dto.response.StoreCreateResponse;
import com.programmers.heavenpay.store.dto.response.StoreDeleteResponse;
import com.programmers.heavenpay.store.dto.response.StoreInfoResponse;
import com.programmers.heavenpay.store.dto.response.StoreUpdateResponse;
import com.programmers.heavenpay.store.service.StoreService;
import org.junit.jupiter.api.DisplayName;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(StoreController.class)
class StoreControllerTest {
    private Long id = 1L;
    private String name = "파리바게뜨";
    private String typeStr = "서비스업";
    private String vendorCode = "108-15-84292";

    @Autowired
    private MockMvc mockMvc;   //DispatcherServlet이 요청을 처리하는 과정을 확인

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StoreService storeService;

    @MockBean    // container가 필요하다면 MockBean
    private ResponseConverter responseConverter;

    @MockBean
    private Pageable pageable;

    @MockBean
    private Page<StoreInfoResponse> storePage;

    private StoreCreateResponse storeCreateResponse = StoreCreateResponse.builder().build();
    private StoreDeleteResponse storeDeleteResponse = StoreDeleteResponse.builder().build();
    private StoreUpdateResponse storeUpdateResponse = StoreUpdateResponse.builder().build();
    private StoreInfoResponse storeInfoResponse = StoreInfoResponse.builder().build();

    @Test
    @DisplayName("post 요청으로 store를 삽입할 수 있다.")
    void insertSuccessTest() throws Exception {
        // Given
        StoreCreateRequest request = StoreCreateRequest.builder()
                .vendorCode(vendorCode)
                .type(typeStr)
                .name(name)
                .build();

        EntityModel<StoreCreateResponse> entityModel = EntityModel.of(
                storeCreateResponse,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(id).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(id).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(id).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        // When
        when(storeService.create(anyString(), anyString(),  anyString()))
                .thenReturn(storeCreateResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.STORE_INSERT_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.STORE_INSERT_SUCCESS, entityModel)));

        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/stores");
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);  //Controller에서 HAL_JSON_VALUE 과 맞춰줌(헤더정보를 맞춰줌)
        requestBuilder.content(objectMapper.writeValueAsString(request)); // request를 String형태로 변환해서 requestbody에 삽입
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE); // Accept라는 헤더를 HAL_JSON_VALUE로 설정

        // Then
        mockMvc.perform(requestBuilder)  // 컨트롤러 실행
                .andDo(print())  // 결과 출력
                .andExpect(status().isOk());   // 응답 결과 검증
    }

    @Test
    @DisplayName("delete 요청으로 store를 삭제할 수 있다.")
    void deleteSuccessTest() throws Exception {
        // Given
        EntityModel<StoreDeleteResponse> entityModel = EntityModel.of(
                storeDeleteResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(id).withSelfRel().withType(HttpMethod.DELETE.name())
        );

        // When
        when(storeService.delete(anyLong()))
                .thenReturn(storeDeleteResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.STORE_DELETE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.STORE_DELETE_SUCCESS, entityModel)));

        MockHttpServletRequestBuilder requestBuilder = delete("/api/v1/stores/{storeId}", anyLong());
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("patch 요청으로 store를 수정할 수 있다.")
    void updateSuccessTest() throws Exception {
        // Given
        StoreUpdateRequest request = StoreUpdateRequest.builder()
                .name(name)
                .type(typeStr)
                .vendorCode(vendorCode)
                .build();

        EntityModel<StoreUpdateResponse> entityModel = EntityModel.of(
                storeUpdateResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(id).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(id).withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(id).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        // When
        when(storeService.update(anyLong(), anyString(), anyString(), anyString()))
                .thenReturn(storeUpdateResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.STORE_UPDATE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.STORE_UPDATE_SUCCESS, entityModel)));

        MockHttpServletRequestBuilder requestBuilder = patch("/api/v1/stores/{storeId}", id);
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
    void getOneSuccessTest() throws Exception {
        // Given
        EntityModel<StoreInfoResponse> entityModel = EntityModel.of(
                storeInfoResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(id).withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(id).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(id).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        // When
        when(storeService.findByName(anyString()))
                .thenReturn(storeInfoResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.STORE_SEARCH_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.STORE_SEARCH_SUCCESS, entityModel)));

        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/stores/{storeName}", anyString());
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("get 요청으로 모든 스토어를 조회할 수 있다.")
    void getAllSuccessTest() throws Exception {
        // Given
        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        // When
        when(storeService.findAllByPages(pageable))
                .thenReturn(storePage);
        when(responseConverter.toResponseEntity(HttpStatus.OK, ResponseMessage.STORE_SEARCH_SUCCESS, storePage, link))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.STORE_SEARCH_SUCCESS, storePage, link)));

        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/stores");
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(StoreController.class);
    }
}