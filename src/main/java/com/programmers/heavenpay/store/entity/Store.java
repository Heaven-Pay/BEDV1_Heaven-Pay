package com.programmers.heavenpay.store.entity;

import com.programmers.heavenpay.common.entity.BaseEntity;
import com.programmers.heavenpay.product.entitiy.Product;
import com.programmers.heavenpay.store.entity.vo.StoreType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Store extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "store_id", unique = true)
    private Long id;

    @Column(name = "store_name", nullable = false, length = 100)
    private String name;

    @Column(name = "store_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreType type;

    @Column(name = "store_vendor_code", nullable = false, unique = true)
    private String vendorCode;

    public void changeInfo(String name, String typeStr, String vendorCode) {
        this.name = name;
        this.type = StoreType.of(typeStr);
        this.vendorCode = vendorCode;
    }

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Product> products = new ArrayList<>();
}