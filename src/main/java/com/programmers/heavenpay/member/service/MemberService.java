package com.programmers.heavenpay.member.service;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import com.programmers.heavenpay.member.converter.MemberConverter;
import com.programmers.heavenpay.member.dto.response.MemberCreateResponse;
import com.programmers.heavenpay.member.dto.response.MemberDeleteResponse;
import com.programmers.heavenpay.member.dto.response.MemberGetOneResponse;
import com.programmers.heavenpay.member.dto.response.MemberUpdateResponse;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberConverter converter;

    @Transactional
    public MemberCreateResponse create(String email, String name, String phoneNumber, String birth, String gender) {
        Member orginMember = converter.toMemberEntity(email, name, phoneNumber, birth, gender);
        Member memberEntity = memberRepository.save(orginMember);

        return converter.toMemberCreateResponse(memberEntity);
    }

    @Transactional(readOnly = true)
    public MemberGetOneResponse getOne(Long id) {
        return memberRepository.findById(id)
                .map(converter::toMemberFindResponse)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );
    }

    @Transactional(readOnly = true)
    public Page<MemberGetOneResponse> getAll(Pageable pageable){
        return memberRepository.findAll(pageable)
                .map(converter::toMemberFindResponse);
    }

    @Transactional
    public MemberUpdateResponse update(Long id, String email, String name, String phoneNumber, String birth, String gender) {
        Member originMember = memberRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER));

        originMember.changeValues(email, name, phoneNumber, birth, gender);
        return converter.toMemberUpdateResponse(originMember);
    }

    @Transactional
    public MemberDeleteResponse delete(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );

        MemberDeleteResponse result = converter.toMemberDeleteResponse(member);
        memberRepository.delete(member);
        return result;
    }
}