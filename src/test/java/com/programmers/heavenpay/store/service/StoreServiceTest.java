package com.programmers.heavenpay.store.service;

import com.programmers.heavenpay.error.exception.DuplicationException;
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

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Mockito의 Mock 객체를 사용하기 위한 Annotation
class StoreServiceTest {
    @InjectMocks
    StoreService storeService;

    @Mock
    StoreRepository storeRepository;

    @Mock
    StoreConverter storeConverter;

    @Mock
    Store store;

    String name = "파리바게뜨";
    String typeStr = "베이커리";
    String vendorCode = "108-15-84292";
    StoreType storeType = StoreType.BAKERY;

    @Test
    @DisplayName("store를 삽입할 수 있다.")
    void createSuccessTest() {
        //given
        when(storeConverter.toStoreEntity(name, storeType, vendorCode)).thenReturn(store);
        when(storeRepository.save(store)).thenReturn(store);

        //when
        storeService.create(name, typeStr, vendorCode);

        //then
        verify(storeRepository).save(store); // storeRepository의 save가 호출되었는지 확인
    }

    @Test
    @DisplayName("store의 벤더코드가 겹치면 DuplicateDataException가 발생한다.")
    void duplicateExceptionTest() {
        //given
        StoreService storeServiceMock = mock(StoreService.class);
        storeServiceMock.create(name, typeStr, vendorCode);

        //when
        when(storeServiceMock.create(name, typeStr, vendorCode))
                .thenThrow(DuplicationException.class);

        //then
        try{
            storeServiceMock.create(name, typeStr, vendorCode);
        }catch (DuplicationException e){
            System.out.println("DuplicateDataException occurred");
        }
    }

    @Test
    @DisplayName("존재하는 store type이 없으면 NotExistsException 발생한다.")
    void notExistsExceptionTest() {
        //given
        StoreService storeServiceMock = mock(StoreService.class);
        String wrongType = "존재하지않는_스토어타입";

        //when
        when(storeServiceMock.create(name, wrongType, vendorCode))
                .thenThrow(NotExistsException.class);

        //then
        try{
            storeServiceMock.create(name, wrongType, vendorCode);
        }catch (NotExistsException e){
            System.out.println("NotExistsException occurred");
        }
    }

    @Test
    @DisplayName("store를 수정할 수 있다.")
    void updateSuccessTest() {
        //given
        StoreUpdateResponse storeUpdateResponseMock = mock(StoreUpdateResponse.class);
        StoreService storeServiceMock = mock(StoreService.class);

        storeServiceMock.create(name, typeStr, vendorCode);
        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(storeConverter.toStoreUpdateResponse(1L, "updated-name", typeStr, "UPDATEDVC"))
                .thenReturn(storeUpdateResponseMock);

        //when
        storeService.update(1L, "updated-name", typeStr, "UPDATEDVC");

        //then
        verify(storeRepository).findById(1L); // storeRepository의 findById가 호출되었는지 확인
        verify(storeConverter).toStoreUpdateResponse(1L, "updated-name", typeStr, "UPDATEDVC"); // storeRepository의 findById가 호출되었는지 확인
    }

    @Test
    @DisplayName("store name으로 찾을 수 있다.")
    void findByNameSuccessTest() {
        //given
        StoreInfoResponse storeInfoResponseMock = mock(StoreInfoResponse.class);
        StoreService storeServiceMock = mock(StoreService.class);

        storeServiceMock.create(name, typeStr, vendorCode);
        when(storeRepository.findByName(name)).thenReturn(Optional.of(store));
        when(storeConverter.toStoreInfoResponse(store)).thenReturn(storeInfoResponseMock);

        //when
        storeService.findByName(name);

        //then
        verify(storeRepository).findByName(name);
        verify(storeConverter).toStoreInfoResponse(store);
    }

    @Test
    @DisplayName("store 단건 삭제를 할 수 있다.")
    void deleteSuccessTest() {
        //given
        StoreDeleteResponse storeInfoResponseMock = mock(StoreDeleteResponse.class);
        StoreService storeServiceMock = mock(StoreService.class);

        storeServiceMock.create(name, typeStr, vendorCode);
        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(storeConverter.toStoreDeleteResponse(1L)).thenReturn(storeInfoResponseMock);

        //when
        storeService.delete(1L);

        //then
        verify(storeRepository).findById(1L);
        verify(storeConverter).toStoreDeleteResponse(1L);
    }
}