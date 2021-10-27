package com.programmers.heavenpay.store.repository;

import com.programmers.heavenpay.store.entity.Store;
import com.programmers.heavenpay.store.entity.vo.StoreType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * repository test에서는 값 테스트와 연관관계 테스트를 진행한다.
 */

@DataJpaTest  // JPA 관련 테스트 설정만 로드함
class StoreRepositoryTest {
    @Autowired
    private StoreRepository storeRepository;

    @Test
    @DisplayName("store 를 삽입 할 수 있다.")
    void saveTest() {
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

    @Test
    @DisplayName("store name으로 조회할 수 있다.")
    void findByNameTest() {
        //given
        Store expected = Store.builder()
                .name("파리바게뜨")
                .vendorCode("108-15-84292")
                .type(StoreType.BAKERY)
                .build();

        storeRepository.save(expected);

        //when
        Optional<Store> actual = storeRepository.findByName(expected.getName());

        //then
        assertThat(actual.isPresent(), is(true));
        assertThat(actual.get().getId(), is(not(nullValue())));
        assertThat(actual.get().getVendorCode(), is(expected.getVendorCode()));
        assertThat(actual.get().getName(), is(expected.getName()));
    }

    @Test
    @DisplayName("store id로 조회할 수 있다.")
    void findByIdTest() {
        //given
        Store tmp = Store.builder()
                .name("파리바게뜨")
                .vendorCode("108-15-84292")
                .type(StoreType.BAKERY)
                .build();

        Store expected = storeRepository.save(tmp);

        //when
        Optional<Store> actual = storeRepository.findById(expected.getId());

        //then
        assertThat(actual.isPresent(), is(true));
        assertThat(actual.get().getId(), is(expected.getId()));
        assertThat(actual.get().getVendorCode(), is(expected.getVendorCode()));
        assertThat(actual.get().getName(), is(expected.getName()));
    }

    @Test
    @DisplayName("store를 삭제할 수 있다.")
    void deleteTest() {
        //given
        String tmpVendorCode = "108-15-84292";
        Store tmp = Store.builder()
                .name("파리바게뜨")
                .vendorCode(tmpVendorCode)
                .type(StoreType.BAKERY)
                .build();

        Store expected = storeRepository.save(tmp);

        //when
        storeRepository.delete(expected);
        Optional<Store> actual = storeRepository.findById(expected.getId());

        //then
        boolean result = storeRepository.existsStoreByVendorCode(tmpVendorCode);
        assertThat(result, is(false));
    }
}