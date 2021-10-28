package com.programmers.heavenpay.store.converter;

import com.programmers.heavenpay.store.dto.response.StoreCreateResponse;
import com.programmers.heavenpay.store.dto.response.StoreDeleteResponse;
import com.programmers.heavenpay.store.dto.response.StoreInfoResponse;
import com.programmers.heavenpay.store.dto.response.StoreUpdateResponse;
import com.programmers.heavenpay.store.entity.Store;
import com.programmers.heavenpay.store.entity.vo.StoreType;
import org.springframework.stereotype.Component;

@Component
public class StoreConverter {
    public Store toStoreEntity(String name, StoreType type, String vendorCode) {
        return Store.builder()
                .name(name)
                .type(type)
                .vendorCode(vendorCode)
                .build();
    }

    public StoreInfoResponse toStoreInfoResponse(Store store) {
        return StoreInfoResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .type(store.getType().getStoreType())
                .vendorCode(store.getVendorCode())
                .createdAt(store.getCreatedDate())
                .modifiedAt(store.getModifiedDate())
                .build();
    }

    public StoreDeleteResponse toStoreDeleteResponse(Long id) {
        return StoreDeleteResponse.builder()
                .id(id)
                .build();
    }

    public StoreCreateResponse toStoreCreateResponse(Long id) {
        return StoreCreateResponse.builder()
                .id(id)
                .build();
    }

    public StoreUpdateResponse toStoreUpdateResponse(Long id, String name, String typeStr, String vendorCode) {
        return StoreUpdateResponse.builder()
                .id(id)
                .name(name)
                .type(typeStr)
                .vendorCode(vendorCode)
                .build();
    }
}
