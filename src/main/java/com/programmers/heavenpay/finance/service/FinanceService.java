package com.programmers.heavenpay.finance.service;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.DuplicationException;
import com.programmers.heavenpay.finance.converter.FinanceConverter;
import com.programmers.heavenpay.finance.dto.response.FinanceCreateResponse;
import com.programmers.heavenpay.finance.entity.Finance;
import com.programmers.heavenpay.finance.repository.FinanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FinanceService {
    private final FinanceRepository financeRepository;
    private final FinanceConverter financeConverter;

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

    private boolean existsByName(String name) {
        return financeRepository.existsByName(name);
    }
}
