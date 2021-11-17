package com.programmers.heavenpay.wish.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.wish.dto.request.WishCreateRequest;
import com.programmers.heavenpay.wish.dto.response.WishCreateResponse;
import com.programmers.heavenpay.wish.dto.response.WishDeleteResponse;
import com.programmers.heavenpay.wish.dto.response.WishInfoResponse;
import com.programmers.heavenpay.wish.service.WishService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(WishController.class)
class WishControllerTest {
    private Long memberId = 1L;
    private Long productId = 2L;
    private Long wishId = 3L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WishService wishService;

    @MockBean
    private ResponseConverter responseConverter;

    @MockBean
    private Pageable pageable;

    // ### dto define area ### //
    @Mock
    Page<WishInfoResponse> wishInfoResponsePage;

    private WishCreateResponse wishCreateResponse = new WishCreateResponse(wishId, LocalDateTime.now());

    private WishCreateRequest wishCreateRequest = new WishCreateRequest(memberId, productId);

    private WishDeleteResponse wishDeleteResponse = new WishDeleteResponse(wishId);
    // ### end of dto define area ### //

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(WishController.class);
    }

    @Test
    void 위시리스트_추가_성공_테스트() throws Exception {
        //given
        EntityModel<WishCreateResponse> entityModel = EntityModel.of(
                wishCreateResponse,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(wishCreateResponse.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(wishCreateResponse.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(wishCreateResponse.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        //when
        when(wishService.create(memberId, productId))
                .thenReturn(wishCreateResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.WISH_INSERT_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.WISH_INSERT_SUCCESS, entityModel)));

        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/wishs");
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.content(objectMapper.writeValueAsString(wishCreateRequest));
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 위시리스트_단건_삭제_성공_테스트() throws Exception {
        //given
        EntityModel<WishDeleteResponse> entityModel = EntityModel.of(
                wishDeleteResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(wishDeleteResponse.getId()).withSelfRel().withType(HttpMethod.DELETE.name())
        );

        //when
        when(wishService.delete(wishId))
                .thenReturn(wishDeleteResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.WISH_DELETE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.WISH_DELETE_SUCCESS, entityModel)));

        MockHttpServletRequestBuilder requestBuilder = delete("/api/v1/wishs/{wishId}", wishId);
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 특정사용자의_위시리스트_조회_성공_테스트() throws Exception {
        //given
        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        //when
        when(wishService.findAllByPages(memberId, pageable))
                .thenReturn(wishInfoResponsePage);
        when(responseConverter.toResponseEntity(ResponseMessage.WISH_SEARCH_SUCCESS, wishInfoResponsePage, link))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.WISH_SEARCH_SUCCESS, wishInfoResponsePage, link)));


        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/members/{memberId}/wishs", memberId);
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }
}