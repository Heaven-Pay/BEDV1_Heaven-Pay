package com.programmers.heavenpay.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.review.dto.request.ReviewCreateRequest;
import com.programmers.heavenpay.review.dto.request.ReviewUpdateRequest;
import com.programmers.heavenpay.review.dto.response.ReviewCreateResponse;
import com.programmers.heavenpay.review.dto.response.ReviewDeleteResponse;
import com.programmers.heavenpay.review.dto.response.ReviewInfoResponse;
import com.programmers.heavenpay.review.dto.response.ReviewUpdateResponse;
import com.programmers.heavenpay.review.service.ReviewService;
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
@WebMvcTest(ReviewControllerTest.class)
class ReviewControllerTest {
    private Long reviewerId = 4L;
    private String content = "contennt1";
    private int score = 3;
    private Long productId = 2L;
    private Long reviewId = 3L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private ResponseConverter responseConverter;

    @MockBean
    private Pageable pageable;

    // ### dto define area ### //
    @Mock
    private Page<ReviewInfoResponse> reviewInfoResponsePage;

    private ReviewDeleteResponse reviewDeleteResponse = ReviewDeleteResponse.builder()
            .id(reviewId)
            .build();

    private ReviewUpdateResponse reviewUpdateResponse = ReviewUpdateResponse.builder()
            .id(reviewId)
            .modifiedAt(LocalDateTime.now())
            .modifiedAt(LocalDateTime.now())
            .build();

    private ReviewInfoResponse reviewInfoResponse = ReviewInfoResponse.builder()
            .score(score)
            .productId(productId)
            .reviewerId(reviewerId)
            .modifiedAt(LocalDateTime.now())
            .content(content)
            .build();

    private ReviewCreateRequest reviewCreateRequest = ReviewCreateRequest.builder()
            .content(content)
            .productId(productId)
            .reviewerId(reviewerId)
            .score(score)
            .build();

    private ReviewCreateResponse reviewCreateResponse = ReviewCreateResponse.builder()
            .id(reviewId)
            .createdAt(LocalDateTime.now())
            .build();

    private ReviewUpdateRequest reviewUpdateRequest = ReviewUpdateRequest.builder()
            .content(content)
            .score(score)
            .build();
    // ### end of dto define area ### //

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(ReviewController.class);
    }

    @Test
    void 리뷰_등록_성공_테스트() throws Exception {  //TODO: 테스트 실패
        //given
        EntityModel<ReviewCreateResponse> entityModel = EntityModel.of(
                reviewCreateResponse,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(reviewCreateResponse.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(reviewCreateResponse.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(reviewCreateResponse.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        //when
        when(reviewService.create(reviewerId, content, score, productId))
                .thenReturn(reviewCreateResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.REVIEW_INSERT_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.REVIEW_INSERT_SUCCESS, entityModel)));

        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/reviews");
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.content(objectMapper.writeValueAsString(reviewCreateRequest));
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 리뷰_삭제_성공_테스트() throws Exception {  //TODO: 테스트 실패
        //given
        EntityModel<ReviewDeleteResponse> entityModel = EntityModel.of(
                reviewDeleteResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(reviewDeleteResponse.getId()).withSelfRel().withType(HttpMethod.DELETE.name())
        );

        //when
        when(reviewService.delete(reviewId))
                .thenReturn(reviewDeleteResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.REVIEW_DELETE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.REVIEW_DELETE_SUCCESS, entityModel)));

        MockHttpServletRequestBuilder requestBuilder = delete("/api/v1/reviews/{reviewId}", reviewId);
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 리뷰_수정_성공_테스트() throws Exception {  //TODO: 테스트 실패
        //given
        EntityModel<ReviewUpdateResponse> entityModel = EntityModel.of(
                reviewUpdateResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(reviewUpdateResponse.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(reviewUpdateResponse.getId()).withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(reviewUpdateResponse.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        //when
        when(reviewService.update(reviewId, content, score))
                .thenReturn(reviewUpdateResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.REVIEW_UPDATE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.REVIEW_UPDATE_SUCCESS, entityModel)));

        MockHttpServletRequestBuilder requestBuilder = patch("/api/v1/reviews/{reviewId}", reviewId);
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.content(objectMapper.writeValueAsString(reviewUpdateRequest));
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 리뷰_단건_조회_성공_테스트() throws Exception {  //TODO: 테스트 실패
        //given
        String slashValue = String.format("/products/%d/reviews/%d", productId, reviewId);

        EntityModel<ReviewInfoResponse> entityModel = EntityModel.of(
                reviewInfoResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(slashValue).withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(slashValue).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(slashValue).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        //when
        when(reviewService.findById(productId, reviewId))
                .thenReturn(reviewInfoResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.REVIEW_SEARCH_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.REVIEW_SEARCH_SUCCESS, entityModel)));

        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/products/{productId}/reviews/{reviewId}", productId, reviewId);
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 특정상품의_모든_리뷰_조회_성공_테스트() throws Exception {  //TODO: 테스트 실패
        //given
        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        //when
        when(reviewService.findAllByPages(pageable, productId))
                .thenReturn(reviewInfoResponsePage);
        when(responseConverter.toResponseEntity(ResponseMessage.REVIEW_SEARCH_SUCCESS, reviewInfoResponsePage, link))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.REVIEW_SEARCH_SUCCESS, reviewInfoResponsePage, link)));


        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/products/{productId}/reviews", productId);
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }
}