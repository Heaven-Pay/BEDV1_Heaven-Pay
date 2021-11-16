package com.programmers.heavenpay.remittance.converter;

import com.programmers.heavenpay.account.entity.Account;
import com.programmers.heavenpay.finance.entity.Finance;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.remittance.dto.response.RemittanceCreateResponse;
import com.programmers.heavenpay.remittance.dto.response.RemittanceDetailAllResponse;
import com.programmers.heavenpay.remittance.dto.response.RemittanceGetResponse;
import com.programmers.heavenpay.remittance.entity.Remittance;
import org.springframework.stereotype.Component;

@Component
public class RemittanceConverter {
    public Remittance toRemittanceEntity(Member member, Account account, Finance finance, String name, String number, Integer money) {
        return Remittance.builder()
                .member(member)
                .account(account)
                .finance(finance)
                .name(name)
                .number(number)
                .money(money)
                .build();
    }

    public RemittanceCreateResponse toRemittanceCreateResponse(Remittance remittance) {
        return RemittanceCreateResponse.builder()
                .remittanceId(remittance.getId())
                .memberName(remittance.getMember().getName())
                .financeName(remittance.getFinance().getName())
                .remittanceName(remittance.getName())
                .remittanceNumber(remittance.getNumber())
                .remittanceMoney(remittance.getMoney())
                .build();
    }

    public RemittanceGetResponse toRemittanceGetResponse(Remittance remittance) {
        return RemittanceGetResponse.builder()
                .remittanceId(remittance.getId())
                .memberName(remittance.getMember().getName())
                .financeName(remittance.getFinance().getName())
                .remittanceName(remittance.getName())
                .remittanceNumber(remittance.getNumber())
                .remittanceMoney(remittance.getMoney())
                .createdAt(remittance.getCreatedDate())
                .modifiedAt(remittance.getModifiedDate())
                .build();
    }

    public RemittanceDetailAllResponse toRemittanceDetailAllResponse(Remittance remittance) {
        return RemittanceDetailAllResponse.builder()
                .remittanceId(remittance.getId())
                .memberName(remittance.getName())
                .financeName(remittance.getFinance().getName())
                .remittanceName(remittance.getName())
                .remittanceMoney(remittance.getMoney())
                .remittanceMoney(remittance.getMoney())
                .createdAt(remittance.getCreatedDate())
                .modifiedAt(remittance.getModifiedDate())
                .build();
    }
}