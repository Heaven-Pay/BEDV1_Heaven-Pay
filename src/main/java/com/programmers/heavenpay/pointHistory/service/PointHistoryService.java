package com.programmers.heavenpay.pointHistory.service;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.member.repository.MemberRepository;
import com.programmers.heavenpay.pointHistory.converter.PointHistoryConverter;
import com.programmers.heavenpay.pointHistory.dto.response.PointHistoryCreateResponse;
import com.programmers.heavenpay.pointHistory.dto.response.PointHistoryDeleteResponse;
import com.programmers.heavenpay.pointHistory.dto.response.PointHistoryGetOneResponse;
import com.programmers.heavenpay.pointHistory.dto.response.PointHistoryUpdateResponse;
import com.programmers.heavenpay.pointHistory.entity.PointHistory;
import com.programmers.heavenpay.pointHistory.repository.PointHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointHistoryService {
    private final PointHistoryRepository pointHistoryRepository;
    private final MemberRepository memberRepository;
    private final PointHistoryConverter converter;

    public PointHistoryService(PointHistoryRepository pointHistoryRepository, MemberRepository memberRepository, PointHistoryConverter converter) {
        this.pointHistoryRepository = pointHistoryRepository;
        this.memberRepository = memberRepository;
        this.converter = converter;
    }

    @Transactional
    public PointHistoryCreateResponse create(Long memberId, String appType, String description, Integer usePoint) {
        Member originMember = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );

        PointHistory orginPointHistory = converter.toPointHistoryEntity(originMember, appType, description, usePoint);
        PointHistory pointHistoryEntity = pointHistoryRepository.save(orginPointHistory);

        return converter.toPointHistoryCreateResponse(pointHistoryEntity);
    }

    @Transactional(readOnly = true)
    public PointHistoryGetOneResponse getOne(Long id, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );
        PointHistory pointHistory = pointHistoryRepository.findByIdAndMember(id, member)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_POINT_HISTORY)
                );;
        return converter.toPointHistoryFindResponse(pointHistory);
    }

    @Transactional(readOnly = true)
    public Page<PointHistoryGetOneResponse> getAll(Long memberId, Pageable pageable){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );
        Page<PointHistory> allByMember = pointHistoryRepository.findAllByMember(member, pageable);
        return allByMember.map(converter::toPointHistoryFindResponse);
    }

    @Transactional
    public PointHistoryUpdateResponse update(Long id, Long memberId, String description, int usePoint) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );

        PointHistory orginPointHistory = pointHistoryRepository.findByIdAndMember(id, member)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_POINT_HISTORY));

        orginPointHistory.updateData(description, usePoint);
        return converter.toPointHistoryUpdateResponse(orginPointHistory);
    }

    @Transactional
    public PointHistoryDeleteResponse delete(Long id, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );
        PointHistory pointHistory = pointHistoryRepository.findById(id)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_POINT_HISTORY)
                );
        PointHistoryDeleteResponse result = converter.toPointHistoryDeleteResponse(pointHistory);

        pointHistoryRepository.deleteByIdAndMember(id, member);
        return result;
    }
}
