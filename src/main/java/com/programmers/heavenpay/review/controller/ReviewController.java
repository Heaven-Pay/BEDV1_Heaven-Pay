package com.programmers.heavenpay.review.controller;

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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1", produces = MediaTypes.HAL_JSON_VALUE)
@Api(value = "Review API")
public class ReviewController {
    private final ReviewService reviewService;
    private final ResponseConverter responseConverter;

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(ReviewController.class);
    }

    @ApiOperation("Review 신규 추가, 성공시 생성된 Review ID 반환")
    @PostMapping(value = "/reviews", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> insert(@Valid @RequestBody ReviewCreateRequest request) {
        ReviewCreateResponse response = reviewService.create(request.getReviewerId(), request.getContent(), request.getScore(), request.getProductId());

        EntityModel<ReviewCreateResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.REVIEW_INSERT_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("Review 단건 삭제, 성공시 삭제된 review ID 반환")
    @DeleteMapping(value = "/reviews/{reviewId}")
    public ResponseEntity<ResponseDto> delete(@Valid @PathVariable Long reviewId) {  // TODO: reviewer id 와 미스매치 인지 확인
        ReviewDeleteResponse response = reviewService.delete(reviewId);

        EntityModel<ReviewDeleteResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.REVIEW_DELETE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("Review 단건 수정, 성공시 수정된 날짜 정보 반환")
    @PatchMapping(value = "/reviews/{reviewId}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> update(@PathVariable Long reviewId, @Valid @RequestBody ReviewUpdateRequest request) {
        ReviewUpdateResponse response = reviewService.update(reviewId, request.getContent(), request.getScore());

        EntityModel<ReviewUpdateResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.REVIEW_UPDATE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("Review 단건 조회, 성공시 Review 정보 반환")
    @GetMapping(value = "/products/{productId}/reviews/{reviewId}")
    public ResponseEntity<ResponseDto> getOne(@PathVariable Long productId, @PathVariable Long reviewId) {
        ReviewInfoResponse response = reviewService.findById(productId, reviewId);

        String slashValue = String.format("/products/%d/reviews/%d", productId, reviewId);

        EntityModel<ReviewInfoResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(slashValue).withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(slashValue).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(slashValue).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.REVIEW_SEARCH_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("특정 상품의 모든 Review 조회, 성공시 Review Page 반환")
    @GetMapping(value = "/products/{productId}/reviews")
    public ResponseEntity<ResponseDto> getAll(Pageable pageable, @PathVariable Long productId) {
        Page<ReviewInfoResponse> responses = reviewService.findAllByPages(pageable, productId);

        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        return responseConverter.toResponseEntity(
                ResponseMessage.REVIEW_SEARCH_SUCCESS,
                responses,
                link
        );
    }
}
