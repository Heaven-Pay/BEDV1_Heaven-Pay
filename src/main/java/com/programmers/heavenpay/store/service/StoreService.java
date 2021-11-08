package com.programmers.heavenpay.store.service;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.DuplicationException;
import com.programmers.heavenpay.error.exception.NotExistsException;
import com.programmers.heavenpay.store.converter.StoreConverter;
import com.programmers.heavenpay.store.dto.response.StoreCreateResponse;
import com.programmers.heavenpay.store.dto.response.StoreDeleteResponse;
import com.programmers.heavenpay.store.dto.response.StoreInfoResponse;
import com.programmers.heavenpay.store.dto.response.StoreUpdateResponse;
import com.programmers.heavenpay.store.entity.Store;
import com.programmers.heavenpay.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class StoreService {
    private final StoreRepository storeRepository;
    private final StoreConverter storeConverter;

    public StoreService(StoreRepository storeRepository, StoreConverter storeConverter) {
        this.storeRepository = storeRepository;
        this.storeConverter = storeConverter;
    }

    @Transactional
    public StoreCreateResponse create(String name, String typeStr, String vendorCode) {
        validateVendorCode(vendorCode);

        Store store = storeConverter.toStoreEntity(name, typeStr, vendorCode);
        Store storeEntity = storeRepository.save(store);

        return storeConverter.toStoreCreateResponse(storeEntity.getId(), storeEntity.getCreatedDate());
    }

    @Transactional
    public StoreUpdateResponse update(Long id, String name, String typeStr, String vendorCode) {
        validateVendorCode(vendorCode);

        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_STORE));

        store.changeInfo(name, typeStr, vendorCode);

        return storeConverter.toStoreUpdateResponse(id, name, typeStr, vendorCode, store.getCreatedDate(), store.getModifiedDate());
    }

    @Transactional(readOnly = true)
    public StoreInfoResponse findByName(String name) {
        Store store = storeRepository.findByName(name)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_STORE));

        return storeConverter.toStoreInfoResponse(store);
    }

    @Transactional(readOnly = true)
    public Page<StoreInfoResponse> findAllByPages(Pageable pageable) {
        Page<Store> storePage = storeRepository.findAll(pageable);

        return storePage.map(storeConverter::toStoreInfoResponse);
    }

    @Transactional
    public StoreDeleteResponse delete(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_STORE));

        storeRepository.delete(store);

        return storeConverter.toStoreDeleteResponse(id);
    }

    private void validateVendorCode(String vendorCode) {
        if (storeRepository.existsStoreByVendorCode(vendorCode)) {
            throw new DuplicationException(ErrorMessage.ALREADY_EXISTS_VENDOR_CODE);
        }
    }
}
