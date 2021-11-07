package com.programmers.heavenpay.pointWallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.pointWallet.dto.request.PointWalletCreateRequest;
import com.programmers.heavenpay.pointWallet.dto.request.PointWalletDeleteRequest;
import com.programmers.heavenpay.pointWallet.dto.request.PointWalletGetOneRequest;
import com.programmers.heavenpay.pointWallet.dto.request.PointWalletUpdateRequest;
import com.programmers.heavenpay.pointWallet.dto.response.PointWalletCreateResponse;
import com.programmers.heavenpay.pointWallet.dto.response.PointWalletDeleteResponse;
import com.programmers.heavenpay.pointWallet.dto.response.PointWalletGetOneResponse;
import com.programmers.heavenpay.pointWallet.dto.response.PointWalletUpdateResponse;
import com.programmers.heavenpay.pointWallet.service.PointWalletService;
import io.swagger.models.HttpMethod;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PointWalletController.class)
class PointWalletControllerTest {
    private static final Long POINT_WALLET_ID = 1L;
    private static final Long MEMBER_ID = 2L;
    private static final Long ACCOUNT_ID = 3L;
    private static final Integer WALLET_POINT = 10000;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PointWalletService pointWalletService;

    @MockBean
    private ResponseConverter responseConverter;

    @MockBean
    private Pageable pageable;

    @MockBean
    private Page<PointWalletGetOneResponse> pointWalletGetOneResponses;

    private PointWalletCreateRequest pointWalletCreateRequest = new PointWalletCreateRequest(MEMBER_ID, ACCOUNT_ID, WALLET_POINT);

    private PointWalletCreateResponse pointWalletCreateResponse = PointWalletCreateResponse.builder()
            .id(POINT_WALLET_ID)
            .build();

    private PointWalletGetOneRequest pointWalletGetRequest = new PointWalletGetOneRequest(MEMBER_ID, ACCOUNT_ID);

    private PointWalletGetOneResponse pointWalletGetOneResponse = PointWalletGetOneResponse.builder()
            .id(POINT_WALLET_ID)
            .walletPoint(WALLET_POINT)
            .build();

    private PointWalletUpdateRequest pointWalletUpdateRequest = new PointWalletUpdateRequest(MEMBER_ID, ACCOUNT_ID, WALLET_POINT);

    private PointWalletUpdateResponse pointWalletUpdateResponse = PointWalletUpdateResponse.builder()
            .id(POINT_WALLET_ID)
            .walletPoint(WALLET_POINT)
            .build();

    private PointWalletDeleteRequest pointWalletDeleteRequest = new PointWalletDeleteRequest(MEMBER_ID, ACCOUNT_ID);

    private PointWalletDeleteResponse pointWalletDeleteResponse = PointWalletDeleteResponse.builder()
            .id(POINT_WALLET_ID)
            .walletPoint(WALLET_POINT)
            .build();

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(PointWalletController.class);
    }

    @Test
    void addWallet() throws Exception {
        // given
        EntityModel<PointWalletCreateResponse> entityModel = EntityModel.of(
                pointWalletCreateResponse,
                getLinkToAddress().withSelfRel().withTitle(HttpMethod.POST.name()),
                getLinkToAddress().slash(pointWalletCreateResponse.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(pointWalletCreateResponse.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(pointWalletCreateResponse.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        when(pointWalletService.create(MEMBER_ID, ACCOUNT_ID, WALLET_POINT))
                .thenReturn(pointWalletCreateResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.POINT_WALLET_INSERT_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.POINT_WALLET_INSERT_SUCCESS, entityModel)));

        // Then
        mockMvc.perform(post("/api/v1/point_wallets")
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(pointWalletCreateRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void changePoint() throws Exception {
        // given
        EntityModel<PointWalletUpdateResponse> entityModel = EntityModel.of(
                pointWalletUpdateResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(pointWalletUpdateResponse.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(pointWalletUpdateResponse.getId()).withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(pointWalletUpdateResponse.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        // when
        when(pointWalletService.update(POINT_WALLET_ID, MEMBER_ID, ACCOUNT_ID, WALLET_POINT))
                .thenReturn(pointWalletUpdateResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.POINT_WALLET_UPDATE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.POINT_WALLET_UPDATE_SUCCESS, entityModel)));

        // then
        mockMvc.perform(put("/api/v1/point_wallets/{pointWalletId}", POINT_WALLET_ID)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(pointWalletUpdateRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteWallet() throws Exception {
        // given
        EntityModel<PointWalletDeleteResponse> entityModel = EntityModel.of(
                pointWalletDeleteResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(pointWalletDeleteResponse.getId()).withSelfRel().withType(HttpMethod.DELETE.name())
        );

        // when
        when(pointWalletService.delete(POINT_WALLET_ID, MEMBER_ID, ACCOUNT_ID))
                .thenReturn(pointWalletDeleteResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.POINT_WALLET_DELETE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.POINT_WALLET_DELETE_SUCCESS, entityModel)));

        // then
        mockMvc.perform(delete("/api/v1/point_wallets/{pointWalletId}", POINT_WALLET_ID)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(pointWalletDeleteRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void findOne() throws Exception {
        // given
        EntityModel<PointWalletGetOneResponse> entityModel = EntityModel.of(
                pointWalletGetOneResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(pointWalletGetOneResponse.getId()).withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(pointWalletGetOneResponse.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(pointWalletGetOneResponse.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        // when
        when(pointWalletService.getOne(POINT_WALLET_ID, MEMBER_ID, ACCOUNT_ID))
                .thenReturn(pointWalletGetOneResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.POINT_WALLET_READ_ONE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.POINT_WALLET_READ_ONE_SUCCESS, entityModel)));

        // then
        mockMvc.perform(get("/api/v1/point_wallets/{pointWalletId}", POINT_WALLET_ID)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(pointWalletGetRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void findAll() throws Exception {
        // given
        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        // when
        when(pointWalletService.getAll(pageable)).thenReturn(pointWalletGetOneResponses);
        when(responseConverter.toResponseEntity(ResponseMessage.POINT_WALLET_READ_ONE_SUCCESS, pointWalletGetOneResponses, link))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.POINT_WALLET_READ_ONE_SUCCESS, pointWalletGetOneResponses, link)));

        // then
        mockMvc.perform(get("/api/v1/point_wallets")
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(pointWalletGetRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}