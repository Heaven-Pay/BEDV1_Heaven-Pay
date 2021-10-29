package com.programmers.heavenpay.finance.repository;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import com.programmers.heavenpay.finance.entity.Finance;
import com.programmers.heavenpay.finance.entity.vo.FinanceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FinanceRepositoryTest {
    @Autowired
    FinanceRepository financeRepository;

    @DisplayName("금융_정보_생성")
    @Test
    void create() {
        // given
        String name = "국민은행";
        FinanceType type = FinanceType.BANK;

        Finance finance = Finance.builder()
                .name(name)
                .financeType(type)
                .build();

        // when
        Finance financeEntity = financeRepository.save(finance);

        // then
        assertAll("Test Finance Create",
                () -> assertEquals(name, financeEntity.getName(), "Fail To Get Name"),
                () -> assertEquals(type, financeEntity.getFinanceType(), "Fail To Get Type")
        );
    }

    @DisplayName("금융_정보_단건_조회")
    @Test
    void get() {
        // given
        String name = "국민은행";
        FinanceType type = FinanceType.BANK;

        Finance finance = Finance.builder()
                .name(name)
                .financeType(type)
                .build();
        Finance savedEntity = financeRepository.save(finance);

        // when
        Finance financeEntity = financeRepository.findById(savedEntity.getId())
                .orElseThrow(() -> {
                    throw new NotExistsException(ErrorMessage.NOT_EXIST_FINANCE);
                });

        // then
        assertAll("Test Finance Create",
                () -> assertEquals(name, financeEntity.getName(), "Fail To Get Name"),
                () -> assertEquals(type, financeEntity.getFinanceType(), "Fail To Get Type")
        );
    }

    @DisplayName("금융_정보_전체_조회")
    @Test
    void getAll() {
        // given
        String name = "국민은행";
        FinanceType type = FinanceType.BANK;

        Finance finance = Finance.builder()
                .name(name)
                .financeType(type)
                .build();
        Finance savedEntity = financeRepository.save(finance);

        // when
        List<Finance> finances = financeRepository.findAll();

        // then
        assertAll("Test Finance Create",
                () -> assertEquals(name, finances.get(0).getName(), "Fail To Get Name"),
                () -> assertEquals(type, finances.get(0).getFinanceType(), "Fail To Get Type")
        );
    }

    @DisplayName("금융_정보_명칭_존재여부")
    @Test
    void existsByName() {
        // given
        String name = "국민은행";
        FinanceType type = FinanceType.BANK;

        Finance finance = Finance.builder()
                .name(name)
                .financeType(type)
                .build();
        financeRepository.save(finance);

        // when
        boolean actual = financeRepository.existsByName(name);

        // then
        assertEquals(true, actual);
    }
}