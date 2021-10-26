package com.programmers.heavenpay.store.converter;

import com.programmers.heavenpay.store.dto.response.StoreInfoResponse;
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
                .type(StoreType.of(store.getType()))
                .vendorCode(store.getVendorCode())
                .createdAt(store.getCreatedDate())
                .modifiedAt(store.getModifiedDate())
                .build();
    }
}
