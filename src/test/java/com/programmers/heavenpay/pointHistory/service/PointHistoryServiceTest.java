package com.programmers.heavenpay.pointHistory.service;

import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.member.entity.vo.GenderType;
import com.programmers.heavenpay.member.repository.MemberRepository;
import com.programmers.heavenpay.pointHistory.converter.PointHistoryConverter;
import com.programmers.heavenpay.pointHistory.dto.response.PointHistoryGetOneResponse;
import com.programmers.heavenpay.pointHistory.entity.PointHistory;
import com.programmers.heavenpay.pointHistory.entity.vo.UsedAppType;
import com.programmers.heavenpay.pointHistory.repository.PointHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PointHistoryServiceTest {
    private static final Long POINT_HISTORY_ID = 1L;
    private static final UsedAppType USED_APP_TYPE = UsedAppType.GIFT;
    private static final String DESCRIPTION = "포인트 결제 내역 소개 설명 글...";
    private static final Integer USE_POINT = 1000;

    private static final Long MEMBER_ID = 1L;
    private static final String EMAIL = "ddkk94@naver.com";
    private static final String NAME =  "Taid";
    private static final String PHONE_NUMBER = "01011223344";
    private static final String BIRTH = "20211015";
    //todo : GenderType enum에서 final String으로 변경
    private static final GenderType GENDER = GenderType.MALE;

    @InjectMocks
    private PointHistoryService pointHistoryService;

    @Mock
    private PointHistoryRepository pointHistoryRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PointHistoryConverter converter;

    @Mock
    private Pageable pageable;

    @Mock
    private Page<PointHistory> pages;

    private PointHistory pointHistory = PointHistory.builder()
            .id(POINT_HISTORY_ID)
            .usedApp(USED_APP_TYPE)
            .description(DESCRIPTION)
            .usePoint(USE_POINT)
            .build();

    private Member member = Member.builder()
            .id(MEMBER_ID)
            .email(EMAIL)
            .name(NAME)
            .phoneNumber(PHONE_NUMBER)
            .birth(BIRTH)
            .gender(GENDER)
            .build();

    private PointHistoryGetOneResponse getOneResponse = PointHistoryGetOneResponse.builder()
            .id(POINT_HISTORY_ID)
            .usedApp(USED_APP_TYPE)
            .description(DESCRIPTION)
            .usePoint(USE_POINT)
            .build();

    @Test
    void create() {
        // given
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(converter.toPointHistoryEntity(member, USED_APP_TYPE.getTypeStr(), DESCRIPTION, USE_POINT)).thenReturn(pointHistory);
        when(pointHistoryRepository.save(pointHistory)).thenReturn(pointHistory);

        // when
        pointHistoryService.create(member.getId(), USED_APP_TYPE.getTypeStr(), DESCRIPTION, USE_POINT);

        // then
        verify(converter).toPointHistoryEntity(member, USED_APP_TYPE.getTypeStr(), DESCRIPTION, USE_POINT);
        verify(pointHistoryRepository).save(pointHistory);
        verify(converter).toPointHistoryCreateResponse(pointHistory);
    }

    @Test
    void findById() {
        // given
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(pointHistoryRepository.findByIdAndMember(POINT_HISTORY_ID, member)).thenReturn(Optional.of(pointHistory));
        when(converter.toPointHistoryFindResponse(pointHistory)).thenReturn(getOneResponse);

        // when
        pointHistoryService.getOne(POINT_HISTORY_ID, MEMBER_ID);

        // then
        verify(pointHistoryRepository).findByIdAndMember(POINT_HISTORY_ID, member);
    }

    @Test
    void findAllByPages() {
        // given
        when(pointHistoryRepository.findAllByMember(member, pageable)).thenReturn(pages);
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));

        // when
        pointHistoryService.getAll(member.getId(), pageable);

        // then
        verify(pointHistoryRepository).findAllByMember(member, pageable);
    }

    @Test
    void update() {
        // given
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(pointHistoryRepository.findByIdAndMember(POINT_HISTORY_ID, member)).thenReturn(Optional.of(pointHistory));

        // when
        pointHistoryService.update(POINT_HISTORY_ID, member.getId(), DESCRIPTION, USE_POINT);

        // then
        verify(pointHistoryRepository).findByIdAndMember(POINT_HISTORY_ID, member);
        verify(converter).toPointHistoryUpdateResponse(pointHistory);
    }

    @Test
    void delete() {
        // given
        when(pointHistoryRepository.findById(POINT_HISTORY_ID)).thenReturn(Optional.of(pointHistory));
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));

        // when
        pointHistoryService.delete(POINT_HISTORY_ID, MEMBER_ID);

        // then
        verify(converter).toPointHistoryDeleteResponse(pointHistory);
        verify(pointHistoryRepository).deleteByIdAndMember(pointHistory.getId(), member);
    }
}