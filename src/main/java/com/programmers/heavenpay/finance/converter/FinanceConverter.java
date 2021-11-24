package com.programmers.heavenpay.finance.converter;

import com.programmers.heavenpay.finance.dto.response.FinanceCreateResponse;
import com.programmers.heavenpay.finance.dto.response.FinanceDeleteResponse;
import com.programmers.heavenpay.finance.dto.response.FinanceDetailResponse;
import com.programmers.heavenpay.finance.dto.response.FinanceUpdateResponse;
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
                .financeType(finance.getFinanceType().getFinanceType())
                .createdAt(finance.getCreatedDate())
                .modifiedAt(finance.getModifiedDate())
                .build();
    }

    public FinanceUpdateResponse toFinanceUpdateResponse(Finance finance) {
        return FinanceUpdateResponse.builder()
                .id(finance.getId())
                .financeName(finance.getName())
                .financeType(finance.getFinanceType().getFinanceType())
                .createdAt(finance.getCreatedDate())
                .modifiedAt(finance.getModifiedDate())
                .build();
    }

    public FinanceDetailResponse toFinanceDetailResponse(Finance finance) {
        return FinanceDetailResponse.builder()
                .id(finance.getId())
                .financeName(finance.getName())
                .financeType(finance.getFinanceType().getFinanceType())
                .createdAt(finance.getCreatedDate())
                .modifiedAt(finance.getModifiedDate())
                .build();
    }

    public FinanceDeleteResponse toFinanceDeleteResponse(Finance finance) {
        return FinanceDeleteResponse.builder()
                .id(finance.getId())
                .financeName(finance.getName())
                .financeType(finance.getFinanceType().getFinanceType())
                .createdAt(finance.getCreatedDate())
                .modifiedAt(finance.getModifiedDate())
                .build();
    }
}