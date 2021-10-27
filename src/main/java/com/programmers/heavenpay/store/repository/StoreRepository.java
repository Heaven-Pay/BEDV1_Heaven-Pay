package com.programmers.heavenpay.store.repository;

import com.programmers.heavenpay.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Override
    Optional<Store> findById(Long id);

    @Override
    void delete(Store entity);

    @Override
    <S extends Store> S save(S entity);

    Optional<Store> findByName(String name);

    boolean existsStoreByVendorCode(String vendorCode);
}
