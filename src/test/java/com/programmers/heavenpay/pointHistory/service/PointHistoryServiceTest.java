package com.programmers.heavenpay.pointHistory.service;

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

    @InjectMocks
    PointHistoryService pointHistoryService;

    @Mock
    PointHistoryRepository pointHistoryRepository;

    @Mock
    PointHistoryConverter converter;

    @Mock
    Pageable pageable;

    @Mock
    Page<PointHistory> pages;

    PointHistory pointHistory = PointHistory.builder()
            .id(POINT_HISTORY_ID)
            .usedApp(USED_APP_TYPE)
            .description(DESCRIPTION)
            .usePoint(USE_POINT)
            .build();

    PointHistoryGetOneResponse getOneResponse = PointHistoryGetOneResponse.builder()
            .id(POINT_HISTORY_ID)
            .usedApp(USED_APP_TYPE)
            .description(DESCRIPTION)
            .usePoint(USE_POINT)
            .build();

    @Test
    void create() {
        // given
        when(converter.toPointHistoryEntity(USED_APP_TYPE.getTypeStr(), DESCRIPTION, USE_POINT)).thenReturn(pointHistory);
        when(pointHistoryRepository.save(pointHistory)).thenReturn(pointHistory);

        // when
        pointHistoryService.create(USED_APP_TYPE.getTypeStr(), DESCRIPTION, USE_POINT);

        // then
        verify(converter).toPointHistoryEntity(USED_APP_TYPE.getTypeStr(), DESCRIPTION, USE_POINT);
        verify(pointHistoryRepository).save(pointHistory);
        verify(converter).toPointHistoryCreateResponse(pointHistory);
    }

    @Test
    void findById() {
        // given
        when(pointHistoryRepository.findById(POINT_HISTORY_ID)).thenReturn(Optional.of(pointHistory));
        when(converter.toPointHistoryFindResponse(pointHistory)).thenReturn(getOneResponse);

        // when
        pointHistoryService.findById(POINT_HISTORY_ID);

        // then
        verify(pointHistoryRepository).findById(POINT_HISTORY_ID);
    }

    @Test
    void findAllByPages() {
        // given
        when(pointHistoryRepository.findAll(pageable)).thenReturn(pages);

        // when
        pointHistoryService.findAllByPages(pageable);

        // then
        verify(pointHistoryRepository).findAll(pageable);
    }

    @Test
    void update() {
        // given
        when(pointHistoryRepository.findById(POINT_HISTORY_ID)).thenReturn(Optional.of(pointHistory));

        // when
        pointHistoryService.update(POINT_HISTORY_ID, USED_APP_TYPE.getTypeStr(), DESCRIPTION, USE_POINT);

        // then
        verify(pointHistoryRepository).findById(POINT_HISTORY_ID);
        verify(converter).toPointHistoryUpdateResponse(pointHistory);
    }

    @Test
    void delete() {
        // given
        when(pointHistoryRepository.findById(POINT_HISTORY_ID)).thenReturn(Optional.of(pointHistory));

        // when
        pointHistoryService.delete(POINT_HISTORY_ID);

        // then
        verify(converter).toPointHistoryDeleteResponse(pointHistory);
        verify(pointHistoryRepository).delete(pointHistory);
    }
}