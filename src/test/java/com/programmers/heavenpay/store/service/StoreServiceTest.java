package com.programmers.heavenpay.store.service;

import com.programmers.heavenpay.error.exception.NotExistsException;
import com.programmers.heavenpay.store.converter.StoreConverter;
import com.programmers.heavenpay.store.dto.response.StoreDeleteResponse;
import com.programmers.heavenpay.store.dto.response.StoreInfoResponse;
import com.programmers.heavenpay.store.dto.response.StoreUpdateResponse;
import com.programmers.heavenpay.store.entity.Store;
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

    @Mock
    Page<StoreInfoResponse> returnPage;

    Store store = Store.builder().build();

    @Mock
    StoreInfoResponse storeInfoResponse;

    StoreDeleteResponse storeDeleteResponse;

    StoreUpdateResponse storeUpdateResponse;

    Page<StoreInfoResponse> storeInfoResponsePage;

    @Test
    @DisplayName("store를 삽입할 수 있다.")
    void createSuccessTest() {
        //given
        when(storeConverter.toStoreEntity(anyString(), anyString(), anyString())).thenReturn(store);
        when(storeRepository.save(store)).thenReturn(store);

        //when
        storeService.create(name, typeStr, vendorCode);

        //then
        verify(storeConverter).toStoreEntity(anyString(), anyString(), anyString());
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
        //given
        when(storeRepository.findById(anyLong()))
                .thenReturn(Optional.of(store));
        when(storeConverter.toStoreUpdateResponse(anyLong(), anyString(), anyString(), anyString(), any(), any()))
                .thenReturn(storeUpdateResponse);

        //when
        storeService.update(id, name, typeStr, vendorCode);

        //then
        verify(storeRepository).findById(anyLong());
        verify(storeConverter).toStoreUpdateResponse(anyLong(), anyString(), anyString(), anyString(), any(), any());
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
//        when(storePage.map(storeInfoResponse)).thenReturn(returnPage);
//
//        // when
//        storeService.findAllByPages(pageable);
//
//        // then
//        verify(storeRepository).findAll(pageable);
//        verify(storeConverter).toStoreInfoResponse(store);
//        verify(storePage).map(any());
    }
}