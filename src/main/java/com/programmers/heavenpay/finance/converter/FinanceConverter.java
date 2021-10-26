package com.programmers.heavenpay.finance.converter;

import com.programmers.heavenpay.finance.dto.response.FinanceCreateResponse;
import com.programmers.heavenpay.finance.entity.Finance;
import com.programmers.heavenpay.finance.entity.vo.FinanceType;
import org.springframework.stereotype.Component;

@Component
public class FinanceConverter {
    public Finance toFinanceEntity(String financeName, String financeType) {
        return Finance.builder()
                .name(financeName)
                .financeType(FinanceType.of(financeType))
                .build();
    }

    public FinanceCreateResponse toFinanceCreateResponse(Finance finance) {
        return FinanceCreateResponse.builder()
                .id(finance.getId())
                .financeName(finance.getName())
                .financeType(finance.getName())
                .createdAt(finance.getCreatedDate())
                .modifiedAt(finance.getModifiedDate())
                .build();
    }
}
