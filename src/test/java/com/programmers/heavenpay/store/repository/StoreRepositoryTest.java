package com.programmers.heavenpay.store.repository;

import com.programmers.heavenpay.store.entity.Store;
import com.programmers.heavenpay.store.entity.vo.StoreType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@DataJpaTest
class StoreRepositoryTest {
    @Autowired
    private StoreRepository storeRepository;

    @Test
    @DisplayName("stroe 를 삽입 할 수 있다.")
    void saveTest() {
        //given
        Store store = Store.builder()
                .name("store1")
                .vendorCode(UUID.randomUUID().toString())
                .type(StoreType.BAKERY)
                .build();

        //when
        Store savedStore = storeRepository.save(store);

        //then
        assertThat(savedStore.getId(), is(not(nullValue())));
    }
}