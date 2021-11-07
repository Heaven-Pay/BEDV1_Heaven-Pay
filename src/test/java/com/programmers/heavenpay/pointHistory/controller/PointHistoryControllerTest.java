package com.programmers.heavenpay.pointHistory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.member.entity.vo.GenderType;
import com.programmers.heavenpay.pointHistory.dto.request.PointHistoryCreateRequest;
import com.programmers.heavenpay.pointHistory.dto.request.PointHistoryDeleteRequest;
import com.programmers.heavenpay.pointHistory.dto.request.PointHistoryGetOneRequest;
import com.programmers.heavenpay.pointHistory.dto.request.PointHistoryUpdateRequest;
import com.programmers.heavenpay.pointHistory.dto.response.PointHistoryCreateResponse;
import com.programmers.heavenpay.pointHistory.dto.response.PointHistoryDeleteResponse;
import com.programmers.heavenpay.pointHistory.dto.response.PointHistoryGetOneResponse;
import com.programmers.heavenpay.pointHistory.dto.response.PointHistoryUpdateResponse;
import com.programmers.heavenpay.pointHistory.entity.vo.UsedAppType;
import com.programmers.heavenpay.pointHistory.service.PointHistoryService;
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

import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PointHistoryController.class)
class PointHistoryControllerTest {
    private static final Long MEMBER_ID = 1L;
    private static final String EMAIL = "ddkk94@naver.com";
    private static final String NAME =  "Taid";
    private static final String PHONE_NUMBER = "01011223344";
    private static final String BIRTH = "20211015";
    private static final GenderType GENDER = GenderType.MALE;

    private static final Long POINT_HISTORY_ID = 2L;
    private static final UsedAppType USED_APP_TYPE = UsedAppType.GIFT;
    private static final String DESCRIPTION = "포인트 결제 내역 소개 설명 글...";
    private static final Integer USE_POINT = 1000;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PointHistoryService pointHistoryService;

    @MockBean
    private ResponseConverter responseConverter;

    @MockBean
    private Pageable pageable;

    @MockBean
    private Page<PointHistoryGetOneResponse> getAllResponses;

    @Autowired
    PointHistoryController pointHistoryController;

    // request
    PointHistoryCreateRequest pointHistoryCreateRequest = new PointHistoryCreateRequest(MEMBER_ID, USED_APP_TYPE.getTypeStr(), DESCRIPTION, USE_POINT);
    PointHistoryUpdateRequest pointHistoryUpdateRequest = new PointHistoryUpdateRequest(MEMBER_ID, DESCRIPTION, USE_POINT);
    PointHistoryGetOneRequest pointHistoryGetRequest = new PointHistoryGetOneRequest(MEMBER_ID);
    PointHistoryDeleteRequest pointHistoryDeleteRequest = new PointHistoryDeleteRequest(MEMBER_ID);

    // response
    PointHistoryCreateResponse pointHistoryCreateResponse = PointHistoryCreateResponse.builder()
            .id(POINT_HISTORY_ID)
            .build();
    PointHistoryUpdateResponse pointHistoryUpdateResponse = PointHistoryUpdateResponse.builder()
            .id(POINT_HISTORY_ID)
            .usedApp(USED_APP_TYPE)
            .description(DESCRIPTION)
            .usePoint(USE_POINT)
            .build();
    PointHistoryGetOneResponse pointHistoryGetOneResponse = PointHistoryGetOneResponse.builder()
            .id(POINT_HISTORY_ID)
            .usedApp(USED_APP_TYPE)
            .description(DESCRIPTION)
            .usePoint(USE_POINT)
            .build();
    PointHistoryDeleteResponse pointHistoryDeleteResponse = PointHistoryDeleteResponse.builder()
            .id(POINT_HISTORY_ID)
            .usedApp(USED_APP_TYPE)
            .description(DESCRIPTION)
            .usePoint(USE_POINT)
            .build();

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(PointHistoryController.class);
    }

    @Test
    void insertTest() throws Exception {
        EntityModel<PointHistoryCreateResponse> entityModel = EntityModel.of(pointHistoryCreateResponse,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(POINT_HISTORY_ID).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(POINT_HISTORY_ID).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PUT.name()),
                getLinkToAddress().slash(POINT_HISTORY_ID).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        // when
        when(pointHistoryService.create(MEMBER_ID, USED_APP_TYPE.getTypeStr(), DESCRIPTION, USE_POINT))
                .thenReturn(pointHistoryCreateResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.POINT_HISTORY_CREATE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.POINT_HISTORY_CREATE_SUCCESS, entityModel)));

        // then
        mockMvc.perform(post("/api/v1/point_histories")
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(pointHistoryCreateRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void editDescriptionTest() throws Exception {
        EntityModel<PointHistoryUpdateResponse> entityModel = EntityModel.of(pointHistoryUpdateResponse,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(POINT_HISTORY_ID).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(POINT_HISTORY_ID).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PUT.name()),
                getLinkToAddress().slash(POINT_HISTORY_ID).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        // when
        when(pointHistoryService.update(POINT_HISTORY_ID, MEMBER_ID, DESCRIPTION, USE_POINT))
                .thenReturn(pointHistoryUpdateResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.POINT_HISTORY_UPDATE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.POINT_HISTORY_UPDATE_SUCCESS, entityModel)));

        // then
        mockMvc.perform(put("/api/v1/point_histories/{pointHistoryId}", POINT_HISTORY_ID)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(pointHistoryUpdateRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void removeTest() throws Exception {
        EntityModel<PointHistoryDeleteResponse> entityModel = EntityModel.of(pointHistoryDeleteResponse,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(POINT_HISTORY_ID).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(POINT_HISTORY_ID).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PUT.name()),
                getLinkToAddress().slash(POINT_HISTORY_ID).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        // when
        when(pointHistoryService.delete(POINT_HISTORY_ID, MEMBER_ID))
                .thenReturn(pointHistoryDeleteResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.POINT_HISTORY_DELETE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.POINT_HISTORY_DELETE_SUCCESS, entityModel)));

        // then
        mockMvc.perform(delete("/api/v1/point_histories/{pointHistoryId}", POINT_HISTORY_ID)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(pointHistoryDeleteRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getOneTest() throws Exception {
        EntityModel<PointHistoryGetOneResponse> entityModel = EntityModel.of(pointHistoryGetOneResponse,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(POINT_HISTORY_ID).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(POINT_HISTORY_ID).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PUT.name()),
                getLinkToAddress().slash(POINT_HISTORY_ID).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        // when
        when(pointHistoryService.getOne(POINT_HISTORY_ID, MEMBER_ID))
                .thenReturn(pointHistoryGetOneResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.POINT_HISTORY_READ_ONE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.POINT_HISTORY_READ_ONE_SUCCESS, entityModel)));

        // then
        mockMvc.perform(get("/api/v1/point_histories/{pointHistoryId}", POINT_HISTORY_ID)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(pointHistoryGetRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getAllTest() throws Exception {
        Link link = getLinkToAddress().withSelfRel().withType(io.swagger.models.HttpMethod.GET.name());

        // when
        when(pointHistoryService.getAll(MEMBER_ID, pageable))
                .thenReturn(getAllResponses);
        when(responseConverter.toResponseEntity(ResponseMessage.POINT_HISTORY_READ_ALL_SUCCESS, getAllResponses, link))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.POINT_HISTORY_READ_ALL_SUCCESS, getAllResponses, link)));

        // then
        mockMvc.perform(get("/api/v1/point_histories")
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(pointHistoryGetRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}