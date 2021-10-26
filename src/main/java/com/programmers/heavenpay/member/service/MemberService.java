package com.programmers.heavenpay.member.service;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import com.programmers.heavenpay.member.converter.MemberConverter;
import com.programmers.heavenpay.member.dto.response.MemberFindResponse;
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
    public Long create(String email, String name, String phoneNumber, String birth, String gender) {
        Member member = converter.toMemberEntity(email, name, phoneNumber, birth, gender);
        Member result = memberRepository.save(member);

        return result.getId();
    }

    @Transactional(readOnly = true)
    public MemberFindResponse findById(Long id) {
        return memberRepository.findById(id)
                .map(converter::toMemberFindDResponse)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER_ID)
                );
    }

    @Transactional(readOnly = true)
    public Page<MemberFindResponse> findAllByPages(Pageable pageable){
        return memberRepository.findAll(pageable)
                .map(converter::toMemberFindDResponse);
    }

    @Transactional
    public void update(Long id, String email, String name, String phoneNumber, String birth, String gender) {
        Member originMember = memberRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER_ID));

        originMember.changeValues(email, name, phoneNumber, birth, gender);
    }

    @Transactional
    public void delete(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER_ID)
                );

        memberRepository.delete(member);
    }
}