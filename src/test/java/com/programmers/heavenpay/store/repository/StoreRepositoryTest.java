package com.programmers.heavenpay.store.repository;

import com.programmers.heavenpay.store.entity.Store;
import com.programmers.heavenpay.store.entity.vo.StoreType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * repository test에서는 값 테스트와 연관관계 테스트를 진행한다.
 */

@DataJpaTest
class StoreRepositoryTest {
    @Autowired
    private StoreRepository storeRepository;

    @Test
    @DisplayName("store 를 삽입 할 수 있다.")
    void saveTest(){
        //given
        Store expected = Store.builder()
                .name("파리바게뜨")
                .vendorCode("108-15-84292")
                .type(StoreType.BAKERY)
                .build();

        //when
        Store actual = storeRepository.save(expected);

        //then
        assertThat(actual.getId(), is(not(nullValue())));
        assertThat(actual.getVendorCode(), is(expected.getVendorCode()));
    }
}