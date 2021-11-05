package com.programmers.heavenpay.follow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.follow.dto.request.FollowFindRequest;
import com.programmers.heavenpay.follow.dto.request.FollowRequest;
import com.programmers.heavenpay.follow.dto.response.FollowFindResponse;
import com.programmers.heavenpay.follow.dto.response.FollowResponse;
import com.programmers.heavenpay.follow.entity.vo.FollowStatus;
import com.programmers.heavenpay.follow.service.FollowService;
import io.swagger.models.HttpMethod;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(FollowController.class)
class FollowControllerTest {
    private final Long MEMBER_ID = 1L;
    private final Long FOLLOWER_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FollowService followService;

    @MockBean
    private Pageable pageable;

    @MockBean
    private ResponseConverter responseConverter;

    @MockBean
    private Page<FollowFindResponse> followFindResponses;

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(FollowController.class);
    }

    private FollowRequest request = new FollowRequest(MEMBER_ID, FOLLOWER_ID);

    private FollowResponse response = new FollowResponse(FollowStatus.FOLLOWING, "김동건");

    private FollowFindRequest followFindRequest = new FollowFindRequest(MEMBER_ID);

    @Test
    void 친구추가() throws Exception {
        // given
        EntityModel<FollowResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().withSelfRel().withTitle(HttpMethod.POST.name()),
                getLinkToAddress().withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD + "-follow").withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD + "-follower").withType(HttpMethod.GET.name())
        );

        // when
        when(followService.follow(MEMBER_ID, FOLLOWER_ID)).thenReturn(response);
        when(responseConverter.toResponseEntity(ResponseMessage.FOLLOW_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.FOLLOW_SUCCESS, entityModel)));

        // then
        mockMvc.perform(post("/api/v1/follows")
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 친구삭제() throws Exception {
        // given
        EntityModel<FollowResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().withSelfRel().withType(HttpMethod.DELETE.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD + "-follow").withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD + "-follower").withType(HttpMethod.GET.name())
        );

        // when
        when(followService.unfollow(MEMBER_ID, FOLLOWER_ID)).thenReturn(response);
        when(responseConverter.toResponseEntity(ResponseMessage.UNFOLLOW_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.UNFOLLOW_SUCCESS, entityModel)));

        // then
        mockMvc.perform(delete("/api/v1/follows")
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 내가_팔로우한_사람_목록_출력() throws Exception {
        // given
        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        // when
        when(followService.findFollower(MEMBER_ID, pageable)).thenReturn(followFindResponses);
        when(responseConverter.toResponseEntity(ResponseMessage.FOLLOW_FIND_SUCCESS, followFindResponses, link))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.FOLLOW_FIND_SUCCESS, followFindResponses, link)));

        // then
        mockMvc.perform(get("/api/v1/follows/follow")
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(followFindRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 나를_팔로우한_사람_목록_출력() throws Exception {
        // given
        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        // when
        when(followService.findFollower(MEMBER_ID, pageable)).thenReturn(followFindResponses);
        when(responseConverter.toResponseEntity(ResponseMessage.FOLLOWER_FIND_SUCCESS, followFindResponses, link))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.FOLLOWER_FIND_SUCCESS, followFindResponses, link)));

        // then
        mockMvc.perform(get("/api/v1/follows/follower")
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(followFindRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}