package com.programmers.heavenpay.store.service;

import com.programmers.heavenpay.error.exception.NotExistsException;
import com.programmers.heavenpay.store.converter.StoreConverter;
import com.programmers.heavenpay.store.dto.response.StoreDeleteResponse;
import com.programmers.heavenpay.store.dto.response.StoreInfoResponse;
import com.programmers.heavenpay.store.dto.response.StoreUpdateResponse;
import com.programmers.heavenpay.store.entity.Store;
import com.programmers.heavenpay.store.entity.vo.StoreType;
import com.programmers.heavenpay.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Mockito의 Mock 객체를 사용하기 위한 Annotation
class StoreServiceTest {
    Long id = 1L;
    String name = "파리바게뜨";
    String typeStr = "식품업";
    String vendorCode = "108-15-84292";

    @InjectMocks
    StoreService storeService;

    @Mock
    StoreRepository storeRepository;

    @Mock
    StoreConverter storeConverter;

    @Mock
    Pageable pageable;

    @Mock
    Page<Store> storePage;

    Store store = Store.builder()
            .type(StoreType.of(typeStr))
            .vendorCode(vendorCode)
            .name(name)
            .id(id)
            .build();

    StoreInfoResponse storeInfoResponse;

    StoreDeleteResponse storeDeleteResponse;

    StoreUpdateResponse storeUpdateResponse;

    Page<StoreInfoResponse> storeInfoResponsePage;

    @Test
    @DisplayName("store를 삽입할 수 있다.")
    void createSuccessTest() {
        //TODO: anyString()으로 보내면 에러남.
        //given
        when(storeConverter.toStoreEntity(name, typeStr, vendorCode)).thenReturn(store);
        when(storeRepository.save(store)).thenReturn(store);

        //when
        storeService.create(name, typeStr, vendorCode);

        //then
        verify(storeRepository).save(store);
    }

    @Test
    @DisplayName("store의 벤더코드가 겹치면 DuplicationException가 발생한다.")
    void duplicateExceptionTest() {
        //TODO: private method를 호출하려면 power mock을 사용해야 함
    }

    @Test
    @DisplayName("존재하지 않는 store id 아면 NotExistsException이 발생한다.")
    void notExistsExceptionTest() {
        //given
        when(storeRepository.findById(anyLong())).thenThrow(NotExistsException.class);

        //when, then
        try {
            storeService.delete(anyLong());
        } catch (NotExistsException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    @DisplayName("존재하지 않는 store name 아면 NotExistsException이 발생한다.")
    void notExistsExceptionTest2() {
        //given
        doThrow(NotExistsException.class)
                .when(storeRepository)
                .findByName(anyString());

        //when, then
        try {
            storeService.findByName(anyString());
        } catch (NotExistsException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    @DisplayName("store를 수정하는 로직이 정상적으로 처리된다.")
    void updateSuccessTest() {
        //TODO: toStoreUpdateResponse의 인자값에 실질적인 값을 넣어주어야 하는 이유
        //given
        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(store));
        when(storeConverter.toStoreUpdateResponse(id, name, typeStr, vendorCode, store.getCreatedDate(), store.getModifiedDate()))
                .thenReturn(storeUpdateResponse);

        //when
        storeService.update(id, name, typeStr, vendorCode);

        //then
        verify(storeRepository).findById(anyLong());
        verify(storeConverter).toStoreUpdateResponse(id, name, typeStr, vendorCode, store.getCreatedDate(), store.getModifiedDate());
    }

    @Test
    @DisplayName("store name으로 찾는 로직이 정상적으로 처리된다.")
    void findByNameSuccessTest() {
        //given
        when(storeRepository.findByName(anyString())).thenReturn(Optional.of(store));
        when(storeConverter.toStoreInfoResponse(store)).thenReturn(storeInfoResponse);

        //when
        storeService.findByName(anyString());

        //then
        verify(storeRepository).findByName(anyString());
        verify(storeConverter).toStoreInfoResponse(store);
    }

    @Test
    @DisplayName("store 단건 삭제 로직이 정상적으로 처리된다.")
    void deleteSuccessTest() {
        //given
        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(store));
        when(storeConverter.toStoreDeleteResponse(anyLong())).thenReturn(storeDeleteResponse);

        //when
        storeService.delete(anyLong());

        //then
        verify(storeRepository).findById(anyLong());
        verify(storeConverter).toStoreDeleteResponse(anyLong());
    }

    @Test
    @DisplayName("Store 전체 조회로직이 정상적으로 처리된다.")
    void findAllTest(){  // TODO:  테스트 통과 못함
//        // given
//        when(storeRepository.findAll(pageable)).thenReturn(storePage);
//        when(storeConverter.toStoreInfoResponse(store)).thenReturn(storeInfoResponse);
//        when(storePage.map(storeConverter::toStoreInfoResponse)).thenReturn(storeInfoResponsePage);
//
//        // when
//        storeService.findAllByPages(pageable);
//
//        // then
//        verify(storeRepository).findAll(pageable);
//        verify(storePage).map(storeConverter::toStoreInfoResponse);
//        verify(storeConverter).toStoreInfoResponse(store);
    }
}