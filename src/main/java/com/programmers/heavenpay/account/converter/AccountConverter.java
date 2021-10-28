package com.programmers.heavenpay.account.converter;

import com.programmers.heavenpay.account.dto.response.AccountCreateResponse;
import com.programmers.heavenpay.account.entity.Account;
import com.programmers.heavenpay.finance.entity.Finance;
import com.programmers.heavenpay.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter {
    public Account toAccountEntity(String title, String description, String number, Member member, Finance finance) {
        return Account.builder()
                .title(title)
                .description(description)
                .number(number)
                .member(member)
                .finance(finance)
                .build();
    }

    public AccountCreateResponse toAccountCreateResponse(Account account) {
        return AccountCreateResponse.builder()
                .id(account.getId())
                .title(account.getTitle())
                .description(account.getDescription())
                .number(account.getNumber())
                .createdAt(account.getCreatedDate())
                .modifiedAt(account.getModifiedDate())
                .build();
    }
}
