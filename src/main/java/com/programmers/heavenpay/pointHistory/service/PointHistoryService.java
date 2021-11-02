package com.programmers.heavenpay.pointHistory.service;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import com.programmers.heavenpay.pointHistory.converter.PointHistoryConverter;
import com.programmers.heavenpay.pointHistory.dto.response.PointHistoryCreateResponse;
import com.programmers.heavenpay.pointHistory.dto.response.PointHistoryDeleteResponse;
import com.programmers.heavenpay.pointHistory.dto.response.PointHistoryGetOneResponse;
import com.programmers.heavenpay.pointHistory.dto.response.PointHistoryUpdateResponse;
import com.programmers.heavenpay.pointHistory.entity.PointHistory;
import com.programmers.heavenpay.pointHistory.repository.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointHistoryService {
    private final PointHistoryRepository pointHistoryRepository;
    private final PointHistoryConverter converter;

    @Transactional
    public PointHistoryCreateResponse create(String appType, String description, Integer usePoint) {
        PointHistory orginPointHistory = converter.toPointHistoryEntity(appType, description, usePoint);
        PointHistory pointHistoryEntity = pointHistoryRepository.save(orginPointHistory);

        return converter.toPointHistoryCreateResponse(pointHistoryEntity);
    }

    @Transactional(readOnly = true)
    public PointHistoryGetOneResponse findById(Long id) {
        return pointHistoryRepository.findById(id)
                .map(converter::toPointHistoryFindResponse)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_POINT_HISTORY_ID)
                );
    }

    @Transactional(readOnly = true)
    public Page<PointHistoryGetOneResponse> findAllByPages(Pageable pageable){
        return pointHistoryRepository.findAll(pageable)
                .map(converter::toPointHistoryFindResponse);
    }

    //todo: changevalue 수정
    @Transactional
    public PointHistoryUpdateResponse update(Long id, String appType, String description, int usePoint) {
        PointHistory orginPointHistory = pointHistoryRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_POINT_HISTORY_ID));

        orginPointHistory.changeValues(appType, description, usePoint);
        return converter.toPointHistoryUpdateResponse(orginPointHistory);
    }

    @Transactional
    public PointHistoryDeleteResponse delete(Long id) {
        PointHistory pointHistory = pointHistoryRepository.findById(id)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_POINT_HISTORY_ID)
                );

        PointHistoryDeleteResponse result = converter.toPointHistoryDeleteResponse(pointHistory);
        pointHistoryRepository.delete(pointHistory);
        return result;
    }
}
