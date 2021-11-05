package com.programmers.heavenpay.follow.controller;

import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.follow.dto.request.FollowFindRequest;
import com.programmers.heavenpay.follow.dto.request.FollowRequest;
import com.programmers.heavenpay.follow.dto.response.FollowFindResponse;
import com.programmers.heavenpay.follow.dto.response.FollowResponse;
import com.programmers.heavenpay.follow.service.FollowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Api("Follow")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/follows", produces = MediaTypes.HAL_JSON_VALUE, consumes = MediaTypes.HAL_JSON_VALUE)
public class FollowController {
    private final FollowService followService;
    private final ResponseConverter responseConverter;

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(FollowController.class);
    }

    @ApiOperation("친구 추가하기")
    @PostMapping()
    public ResponseEntity<ResponseDto> follow(@Valid @RequestBody FollowRequest request) {
        FollowResponse response = followService.follow(request.getId(), request.getFollowerId());

        EntityModel<FollowResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().withSelfRel().withTitle(HttpMethod.POST.name()),
                getLinkToAddress().withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD + "-follow").withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD + "-follower").withType(HttpMethod.GET.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.FOLLOW_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("친구 삭제하기")
    @DeleteMapping()
    public ResponseEntity<ResponseDto> unfollow(@Valid @RequestBody FollowRequest request) {
        FollowResponse response = followService.unfollow(request.getId(), request.getFollowerId());

        EntityModel<FollowResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().withSelfRel().withType(HttpMethod.DELETE.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD + "-follow").withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD + "-follower").withType(HttpMethod.GET.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.UNFOLLOW_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("내가 팔로우한 사람 조회")
    @GetMapping(value = "/follow")
    public ResponseEntity<ResponseDto> getAllFollow(@Valid @RequestBody FollowFindRequest request, Pageable pageable) {
        Page<FollowFindResponse> response = followService.findFollow(request.getMemberId(), pageable);

        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        return responseConverter.toResponseEntity(
                ResponseMessage.FOLLOW_FIND_SUCCESS,
                response,
                link
        );
    }

    @ApiOperation("나를 팔로우한 사람 조회")
    @GetMapping(value = "/follower")
    public ResponseEntity<ResponseDto> getAllFollower(@Valid @RequestBody FollowFindRequest request, Pageable pageable) {
        Page<FollowFindResponse> response = followService.findFollower(request.getMemberId(), pageable);

        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        return responseConverter.toResponseEntity(
                ResponseMessage.FOLLOWER_FIND_SUCCESS,
                response,
                link
        );
    }
}