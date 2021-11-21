package com.programmers.heavenpay.member.controller;

import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.member.dto.request.MemberCreateRequest;
import com.programmers.heavenpay.member.dto.request.MemberUpdateRequest;
import com.programmers.heavenpay.member.dto.response.MemberCreateResponse;
import com.programmers.heavenpay.member.dto.response.MemberDeleteResponse;
import com.programmers.heavenpay.member.dto.response.MemberGetOneResponse;
import com.programmers.heavenpay.member.dto.response.MemberUpdateResponse;
import com.programmers.heavenpay.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.HttpMethod;
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

@RestController
@RequestMapping(value = "/api/v1/members", produces = MediaTypes.HAL_JSON_VALUE)
public class MemberController {
    private final MemberService memberService;
    private final ResponseConverter responseConverter;

    public MemberController(MemberService memberService, ResponseConverter responseConverter) {
        this.memberService = memberService;
        this.responseConverter = responseConverter;
    }

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(MemberController.class);
    }

    @ApiOperation("회원(Member) 신규 추가, 성공시 생성된 Member ID 반환")
    @PostMapping(consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> signUp(@Valid @RequestBody MemberCreateRequest request) {
        MemberCreateResponse response = memberService.create(
                request.getEmail(),
                request.getName(),
                request.getPhoneNumber(),
                request.getBirth(),
                request.getGender()
        );

        EntityModel<MemberCreateResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withSelfRel().withTitle(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.MEMBER_CREATE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("회원(Member) 단건 수정, 성공시 수정된 Member 정보 반환")
    @PutMapping(value = "/{memberId}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> editInfo(@PathVariable Long memberId, @Valid @RequestBody MemberUpdateRequest request) {
        MemberUpdateResponse response = memberService.update(
                memberId,
                request.getEmail(),
                request.getName(),
                request.getPhoneNumber(),
                request.getBirth(),
                request.getGender()
        );

        EntityModel<MemberUpdateResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.MEMBER_UPDATE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("회원(Member) 단건 삭제, 성공시 삭제된 Member ID 반환")
    @DeleteMapping(value = "/{memberId}")
    public ResponseEntity<ResponseDto> delete(@Valid @PathVariable Long memberId) {
        MemberDeleteResponse response = memberService.delete(memberId);

        EntityModel<MemberDeleteResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.MEMBER_DELETE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("회원(Member) 단건 조회, 성공시 Member 정보 반환")
    @GetMapping(value = "/{memberId}")
    public ResponseEntity<ResponseDto> readSingleData(@PathVariable Long memberId) {
        MemberGetOneResponse response = memberService.getOne(memberId);

        EntityModel<MemberGetOneResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.MEMBER_READ_ONE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("모든 회원(Member) 조회, 성공시 Member Page 반환")
    @GetMapping()
    public ResponseEntity<ResponseDto> readPage(Pageable pageable) {
        Page<MemberGetOneResponse> responses = memberService.getAll(pageable);

        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        return responseConverter.toResponseEntity(
                ResponseMessage.MEMBER_READ_ALL_SUCCESS,
                responses,
                link
        );
    }
}
