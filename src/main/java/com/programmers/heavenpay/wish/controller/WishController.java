package com.programmers.heavenpay.wish.controller;

import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.wish.dto.request.WishCreateRequest;
import com.programmers.heavenpay.wish.dto.response.WishCreateResponse;
import com.programmers.heavenpay.wish.dto.response.WishDeleteResponse;
import com.programmers.heavenpay.wish.dto.response.WishInfoResponse;
import com.programmers.heavenpay.wish.service.WishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaTypes.HAL_JSON_VALUE)
@Api(tags = "Wish")
public class WishController {
    private final WishService wishService;
    private final ResponseConverter responseConverter;

    public WishController(WishService wishService, ResponseConverter responseConverter) {
        this.wishService = wishService;
        this.responseConverter = responseConverter;
    }

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(WishController.class);
    }

    @ApiOperation("Wish 신규 추가, 성공시 생성된 Wish ID 반환")
    @PostMapping(value = "/wishs", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> insert(@Valid @RequestBody WishCreateRequest request) {
        WishCreateResponse response = wishService.create(request.getMemberId(), request.getProductId());

        EntityModel<WishCreateResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.WISH_INSERT_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("Wish 단건 삭제, 성공시 삭제된 Wish ID 반환")
    @DeleteMapping(value = "/wishs/{wishId}")
    public ResponseEntity<ResponseDto> delete(@Valid @PathVariable Long wishId) {
        WishDeleteResponse response = wishService.delete(wishId);

        EntityModel<WishDeleteResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.WISH_DELETE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("특정 member의 모든 Wish 조회, 성공시 wish Page 반환")
    @GetMapping(value = "/members/{memberId}/wishs")
    public ResponseEntity<ResponseDto> getAll(Pageable pageable, @PathVariable Long memberId) {
        Page<WishInfoResponse> responses = wishService.findAllByPages(memberId, pageable);

        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        return responseConverter.toResponseEntity(
                ResponseMessage.WISH_SEARCH_SUCCESS,
                responses,
                link
        );
    }
}
