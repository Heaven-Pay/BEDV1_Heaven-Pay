package com.programmers.heavenpay.finance.service;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.DuplicationException;
import com.programmers.heavenpay.error.exception.NotExistsException;
import com.programmers.heavenpay.finance.converter.FinanceConverter;
import com.programmers.heavenpay.finance.dto.response.FinanceCreateResponse;
import com.programmers.heavenpay.finance.dto.response.FinanceDeleteResponse;
import com.programmers.heavenpay.finance.dto.response.FinanceDetailResponse;
import com.programmers.heavenpay.finance.dto.response.FinanceUpdateResponse;
import com.programmers.heavenpay.finance.entity.Finance;
import com.programmers.heavenpay.finance.repository.FinanceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class FinanceService {
    private final FinanceRepository financeRepository;
    private final FinanceConverter financeConverter;

    public FinanceService(FinanceRepository financeRepository, FinanceConverter financeConverter) {
        this.financeRepository = financeRepository;
        this.financeConverter = financeConverter;
    }

    @Transactional
    public FinanceCreateResponse create(Long id, String name, String type) {
        if (existsByName(name)) {
            throw new DuplicationException(ErrorMessage.DUPLICATION_FINANCE_NAME);
        }

        Finance financeInstance = financeConverter.toFinanceEntity(name, type);
        financeInstance.addCreatedAndLastModifiedMember(id);

        Finance financeEntity = financeRepository.save(financeInstance);

        return financeConverter.toFinanceCreateResponse(financeEntity);
    }

    @Transactional(readOnly = true)
    public FinanceDetailResponse find(Long financeId) {
        Finance finance = financeRepository.findById(financeId)
                .orElseThrow(() -> {
                    throw new NotExistsException(ErrorMessage.NOT_EXIST_FINANCE);
                });

        return financeConverter.toFinanceDetailResponse(finance);
    }

    @Transactional(readOnly = true)
    public Page<FinanceDetailResponse> findAll(Pageable pageable) {
        return financeRepository.findAll(pageable)
                .map(financeConverter::toFinanceDetailResponse);
    }

    @Transactional
    public FinanceUpdateResponse update(Long memberId, Long financeId, String name, String type) {
        Finance finance = financeRepository.findById(financeId)
                .orElseThrow(() -> {
                    throw new NotExistsException(ErrorMessage.NOT_EXIST_FINANCE);
                });

        if (existsByName(name) && !Objects.equals(finance.getName(), name)) {
            throw new DuplicationException(ErrorMessage.DUPLICATION_FINANCE_NAME);
        }

        finance.update(memberId, name, type);

        return financeConverter.toFinanceUpdateResponse(finance);
    }

    private boolean existsByName(String name) {
        return financeRepository.existsByName(name);
    }

    @Transactional
    public FinanceDeleteResponse delete(Long financeId) {
        Finance finance = financeRepository.findById(financeId)
                .orElseThrow(() -> {
                    throw new NotExistsException(ErrorMessage.NOT_EXIST_FINANCE);
                });

        financeRepository.deleteById(financeId);

        return financeConverter.toFinanceDeleteResponse(finance);
    }
}
