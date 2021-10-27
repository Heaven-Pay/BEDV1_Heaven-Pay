package com.programmers.heavenpay.finance.service;

import com.programmers.heavenpay.finance.converter.FinanceConverter;
import com.programmers.heavenpay.finance.dto.response.FinanceDeleteResponse;
import com.programmers.heavenpay.finance.dto.response.FinanceDetailResponse;
import com.programmers.heavenpay.finance.dto.response.FinanceUpdateResponse;
import com.programmers.heavenpay.finance.entity.Finance;
import com.programmers.heavenpay.finance.repository.FinanceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FinanceServiceTest {
    @InjectMocks
    FinanceService financeService;

    @Mock
    FinanceRepository financeRepository;

    @Mock
    FinanceConverter financeConverter;

    @Mock
    Finance finance;

    Long memberId = 1L;
    Long financeId = 1L;
    String name = "국민은행";
    String type = "은행";
    String updatedName = "신한은행";
    String updatedType = "증권";

    @DisplayName("금융_정보_생성")
    @Test
    void create() {
        // given
        when(financeConverter.toFinanceEntity(name, type)).thenReturn(finance);
        when(financeRepository.save(finance)).thenReturn(finance);

        // when
        financeService.create(memberId, name, type);

        // then
        verify(financeRepository).save(finance);
    }

    @DisplayName("금융_정보_단건_조회")
    @Test
    void get() {
        // given
        FinanceDetailResponse financeDetailResponse = mock(FinanceDetailResponse.class);
        FinanceService financeServiceMock = mock(FinanceService.class);

        financeServiceMock.create(memberId, name, type);
        when(financeRepository.findById(financeId)).thenReturn(Optional.of(finance));
        when(financeConverter.toFinanceDetailResponse(finance)).thenReturn(financeDetailResponse);

        // when
        financeService.getOne(financeId);

        // then
        verify(financeRepository).findById(financeId);
        verify(financeConverter).toFinanceDetailResponse(finance);
    }

    @DisplayName("금융_정보_수정")
    @Test
    void update() {
        //given
        FinanceUpdateResponse financeUpdateResponseMock = mock(FinanceUpdateResponse.class);
        FinanceService financeServiceMock = mock(FinanceService.class);

        financeServiceMock.create(memberId, name, type);

        when(financeRepository.findById(financeId)).thenReturn(Optional.of(finance));
        when(financeConverter.toFinanceUpdateResponse(finance)).thenReturn(financeUpdateResponseMock);

        //when
        financeService.update(financeId, financeId, updatedName, updatedType);

        //then
        verify(financeRepository).findById(financeId);
        verify(financeConverter).toFinanceUpdateResponse(finance);
    }

    @DisplayName("금융_정보_삭제")
    @Test
    void delete() {
        //given
        FinanceDeleteResponse financeDeleteResponseMock = mock(FinanceDeleteResponse.class);
        FinanceService financeServiceMock = mock(FinanceService.class);

        financeServiceMock.create(memberId, name, type);

        when(financeRepository.findById(financeId)).thenReturn(Optional.of(finance));
        when(financeConverter.toFinanceDeleteResponse(finance)).thenReturn(financeDeleteResponseMock);

        //when
        financeService.delete(financeId);

        //then
        verify(financeRepository).findById(financeId);
        verify(financeConverter).toFinanceDeleteResponse(finance);
    }
}