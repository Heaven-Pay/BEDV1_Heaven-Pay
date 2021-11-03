package com.programmers.heavenpay.member.service;

import com.programmers.heavenpay.member.converter.MemberConverter;
import com.programmers.heavenpay.member.dto.response.MemberGetOneResponse;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.member.entity.vo.GenderType;
import com.programmers.heavenpay.member.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    private static final Long MEMBER_ID = 1L;
    private static final String EMAIL = "ddkk94@naver.com";
    private static final String NAME =  "Taid";
    private static final String PHONE_NUMBER = "01011223344";
    private static final String BIRTH = "20211015";
    //todo : GenderType enum에서 final String으로 변경
    private static final GenderType GENDER = GenderType.MALE;

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @Mock
    MemberConverter converter;

    @Mock
    Pageable pageable;

    @Mock
    Page<Member> members;

    Member member = Member.builder()
            .id(MEMBER_ID)
            .email(EMAIL)
            .name(NAME)
            .phoneNumber(PHONE_NUMBER)
            .birth(BIRTH)
            .gender(GENDER)
            .build();

    MemberGetOneResponse findResponse = MemberGetOneResponse.builder()
            .id(MEMBER_ID)
            .email(EMAIL)
            .name(NAME)
            .phoneNumber(PHONE_NUMBER)
            .birth(BIRTH)
            .gender(GENDER)
            .build();

    @Test
    void create() {
        // given
        when(converter.toMemberEntity(EMAIL, NAME, PHONE_NUMBER, BIRTH, GENDER.getTypeStr())).thenReturn(member);
        when(memberRepository.save(member)).thenReturn(member);

        // when
        memberService.create(EMAIL, NAME, PHONE_NUMBER, BIRTH, GENDER.getTypeStr());

        // then
        verify(converter).toMemberEntity(EMAIL, NAME, PHONE_NUMBER, BIRTH, GENDER.getTypeStr());
        verify(memberRepository).save(member);
        verify(converter).toMemberCreateResponse(member);
    }

    @Test
    void findById() {
        // given
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(converter.toMemberFindResponse(member)).thenReturn(findResponse);

        // when
        memberService.findById(MEMBER_ID);

        // then
        verify(memberRepository).findById(MEMBER_ID);
    }

    @Test
    void findAllByPages() {
        // given
        when(memberRepository.findAll(pageable)).thenReturn(members);

        // when
        memberService.findAllByPages(pageable);

        // then
        verify(memberRepository).findAll(pageable);
    }

    @Test
    void update() {
        // given
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));

        // when
        memberService.update(MEMBER_ID, EMAIL, NAME, PHONE_NUMBER, BIRTH, GENDER.getTypeStr());

        // then
        verify(memberRepository).findById(MEMBER_ID);
        verify(converter).toMemberUpdateResponse(member);
    }

    @Test
    void deleteTest() {
        // given
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));

        // when
        memberService.delete(MEMBER_ID);

        // then
        verify(converter).toMemberDeleteResponse(member);
        verify(memberRepository).delete(member);
    }
}