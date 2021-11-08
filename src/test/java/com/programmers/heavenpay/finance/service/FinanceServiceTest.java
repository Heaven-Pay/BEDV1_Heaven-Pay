package com.programmers.heavenpay.finance.service;

import com.programmers.heavenpay.finance.converter.FinanceConverter;
import com.programmers.heavenpay.finance.dto.response.FinanceDeleteResponse;
import com.programmers.heavenpay.finance.dto.response.FinanceDetailResponse;
import com.programmers.heavenpay.finance.dto.response.FinanceUpdateResponse;
import com.programmers.heavenpay.finance.entity.Finance;
import com.programmers.heavenpay.finance.repository.FinanceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FinanceServiceTest {
    private static final Long memberId = 1L;
    private static final Long financeId = 1L;
    private static final String name = "국민은행";
    private static final String type = "은행";
    private static final String updatedName = "신한은행";
    private static final String updatedType = "증권";

    @InjectMocks
    private FinanceService financeService;

    @Mock
    private FinanceRepository financeRepository;

    @Mock
    private FinanceConverter financeConverter;

    @Mock
    private Finance finance;

    @Test
    void 금융_정보_생성() {
        // given
        when(financeConverter.toFinanceEntity(name, type)).thenReturn(finance);
        when(financeRepository.save(finance)).thenReturn(finance);

        // when
        financeService.create(memberId, name, type);

        // then
        verify(financeRepository).save(finance);
    }

    @Test
    void 금융_정보_단건_조회() {
        // given
        FinanceDetailResponse financeDetailResponse = mock(FinanceDetailResponse.class);
        FinanceService financeServiceMock = mock(FinanceService.class);

        financeServiceMock.create(memberId, name, type);
        when(financeRepository.findById(financeId)).thenReturn(Optional.of(finance));
        when(financeConverter.toFinanceDetailResponse(finance)).thenReturn(financeDetailResponse);

        // when
        financeService.find(financeId);

        // then
        verify(financeRepository).findById(financeId);
        verify(financeConverter).toFinanceDetailResponse(finance);
    }

    @Test
    void 금융_정보_수정() {
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

    @Test
    void 금융_정보_삭제() {
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