package com.programmers.heavenpay.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.member.dto.request.*;
import com.programmers.heavenpay.member.dto.response.*;
import com.programmers.heavenpay.member.entity.vo.GenderType;
import com.programmers.heavenpay.member.service.MemberService;
import io.swagger.models.HttpMethod;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(MemberController.class)
class MemberControllerTest {
    private static final Long MEMBER_ID = 1L;
    private static final String EMAIL = "ddkk94@naver.com";
    private static final String NAME =  "Taid";
    private static final String PHONE_NUMBER = "01011223344";
    private static final String BIRTH = "20211015";
    private static final GenderType GENDER = GenderType.MALE;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @MockBean
    private ResponseConverter responseConverter;

    @MockBean
    private Pageable pageable;

    @MockBean
    private Page<MemberGetOneResponse> getPage;

    @Autowired
    MemberController memberController;

    private MemberCreateRequest memberCreateRequest = MemberCreateRequest.builder()
            .email(EMAIL)
            .name(NAME)
            .phoneNumber(PHONE_NUMBER)
            .birth(BIRTH)
            .gender(GENDER.getTypeStr())
            .build();

    private MemberCreateResponse memberCreateResponse = MemberCreateResponse.builder()
            .id(MEMBER_ID)
            .build();

    private MemberGetOneRequest memberGetOneRequest = MemberGetOneRequest.builder()
            .id(MEMBER_ID)
            .build();

    private MemberGetOneResponse memberGetOneResponse = MemberGetOneResponse.builder()
            .id(MEMBER_ID)
            .email(EMAIL)
            .name(NAME)
            .phoneNumber(PHONE_NUMBER)
            .birth(BIRTH)
            .gender(GENDER)
            .build();

    private MemberUpdateRequest memberUpdateRequest = MemberUpdateRequest.builder()
            .email(EMAIL)
            .name(NAME)
            .phoneNumber(PHONE_NUMBER)
            .birth(BIRTH)
            .gender(GENDER.getTypeStr())
            .build();

    private MemberUpdateResponse memberUpdateResponse = MemberUpdateResponse.builder()
            .id(MEMBER_ID)
            .email(EMAIL)
            .name(NAME)
            .phoneNumber(PHONE_NUMBER)
            .birth(BIRTH)
            .gender(GENDER)
            .build();

    private MemberDeleteRequest memberDeleteRequest = MemberDeleteRequest.builder()
            .id(MEMBER_ID)
            .build();

    private MemberDeleteResponse memberDeleteResponse = MemberDeleteResponse.builder()
            .id(MEMBER_ID)
            .email(EMAIL)
            .name(NAME)
            .phoneNumber(PHONE_NUMBER)
            .birth(BIRTH)
            .gender(GENDER)
            .build();

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(MemberController.class);
    }

    @Test
    @DisplayName("Create 테스트")
    void signUpTest() throws Exception {
        // Given
        EntityModel<MemberCreateResponse> entityModel = EntityModel.of(memberCreateResponse,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(MEMBER_ID).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(MEMBER_ID).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PUT.name()),
                getLinkToAddress().slash(MEMBER_ID).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        MemberCreateRequest request = MemberCreateRequest.builder()
                .email("ddkk94@naver.com")
                .name("Taid")
                .phoneNumber("00011112222")
                .birth("19991122")
                .gender(GenderType.MALE.getTypeStr())
                .build();

        // When
        when(memberService.create(EMAIL, NAME, PHONE_NUMBER, BIRTH, GENDER.getTypeStr()))
                .thenReturn(memberCreateResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.MEMBER_CREATE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_CREATE_SUCCESS, entityModel)));

        // Then
        mockMvc.perform(post("/api/v1/members")
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(memberCreateRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Read 테스트")
    void getOneTest() throws Exception {
        // given
        EntityModel<MemberGetOneResponse> entityModel = EntityModel.of(memberGetOneResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(MEMBER_ID).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(MEMBER_ID).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PUT.name()),
                getLinkToAddress().slash(MEMBER_ID).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        // when
        when(memberService.getOne(MEMBER_ID))
                .thenReturn(memberGetOneResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.MEMBER_READ_ONE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_READ_ONE_SUCCESS, entityModel)));

        // then
        mockMvc.perform(get("/api/v1/members/{memberId}", MEMBER_ID)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(memberGetOneRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Read All 테스트")
    void getAllTest() throws Exception {
        // given
        EntityModel<MemberGetOneResponse> entityModel = EntityModel.of(memberGetOneResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(MEMBER_ID).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(MEMBER_ID).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PUT.name()),
                getLinkToAddress().slash(MEMBER_ID).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        // when
        when(memberService.getAll(pageable))
                .thenReturn(getPage);
        when(responseConverter.toResponseEntity(ResponseMessage.MEMBER_READ_ONE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_READ_ONE_SUCCESS, entityModel)));

        // then
        mockMvc.perform(get("/api/v1/members", MEMBER_ID)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(memberGetOneRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Update 테스트")
    void editInfoTest() throws Exception {
        // given
        EntityModel<MemberUpdateResponse> entityModel = EntityModel.of(memberUpdateResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(MEMBER_ID).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(MEMBER_ID).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PUT.name()),
                getLinkToAddress().slash(MEMBER_ID).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        // when
        when(memberService.update(MEMBER_ID, EMAIL, NAME, PHONE_NUMBER, BIRTH, GENDER.getTypeStr()))
                .thenReturn(memberUpdateResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.MEMBER_UPDATE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_UPDATE_SUCCESS, entityModel)));

        // then
        mockMvc.perform(put("/api/v1/members/{memberId}", MEMBER_ID)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(memberUpdateRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Delete 테스트")
    void deleteTest() throws Exception {
        // given
        EntityModel<MemberDeleteResponse> entityModel = EntityModel.of(memberDeleteResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(MEMBER_ID).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(MEMBER_ID).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PUT.name()),
                getLinkToAddress().slash(MEMBER_ID).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        // when
        when(memberService.delete(MEMBER_ID))
                .thenReturn(memberDeleteResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.MEMBER_DELETE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_DELETE_SUCCESS, entityModel)));

        // then
        mockMvc.perform(delete("/api/v1/members/{memberId}", MEMBER_ID)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(memberDeleteRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}