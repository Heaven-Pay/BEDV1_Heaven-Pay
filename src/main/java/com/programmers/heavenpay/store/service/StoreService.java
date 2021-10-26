package com.programmers.heavenpay.store.service;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import com.programmers.heavenpay.store.converter.StoreConverter;
import com.programmers.heavenpay.store.dto.response.StoreCreateResponse;
import com.programmers.heavenpay.store.dto.response.StoreDeleteResponse;
import com.programmers.heavenpay.store.dto.response.StoreInfoResponse;
import com.programmers.heavenpay.store.dto.response.StoreUpdateResponse;
import com.programmers.heavenpay.store.entity.Store;
import com.programmers.heavenpay.store.entity.vo.StoreType;
import com.programmers.heavenpay.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final StoreConverter storeConverter;

    @Transactional
    public StoreCreateResponse create(String name, String typeStr, String vendorCode) {
        StoreType type = StoreType.of(typeStr);

        Store store = storeConverter.toStoreEntity(name, type, vendorCode);
        Store savedStore = storeRepository.save(store);

        return StoreCreateResponse.builder()
                .id(savedStore.getId())
                .build();
    }

    @Transactional
    public StoreUpdateResponse update(Long id, String name, String typeStr, String vendorCode) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_STORE));

        StoreType type = StoreType.of(typeStr);
        store.changeInfo(name, type, vendorCode);

        return StoreUpdateResponse.builder()
                .id(id)
                .name(name)
                .type(typeStr)
                .vendorCode(vendorCode)
                .build();
    }

    @Transactional(readOnly = true)
    public StoreInfoResponse findByName(String name) {
        Store store = storeRepository.findByName(name)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_STORE));

        return storeConverter.toStoreInfoResponse(store);
    }

    @Transactional(readOnly = true)
    public Page<StoreInfoResponse> findAllByPages(Pageable pageable) {
        return storeRepository.findAll(pageable)
                .map(storeConverter::toStoreInfoResponse);
    }

    @Transactional
    public StoreDeleteResponse delete(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_STORE));

        storeRepository.delete(store);

        return StoreDeleteResponse.builder()
                .id(id)
                .build();
    }
}
