package com.programmers.heavenpay.store.entity;

import com.programmers.heavenpay.common.entity.BaseEntity;
import com.programmers.heavenpay.product.entitiy.Product;
import com.programmers.heavenpay.store.entity.vo.StoreType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
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

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    protected Store() {
    }

    public Store(Long id, String name, StoreType type, String vendorCode, List<Product> products) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.vendorCode = vendorCode;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public StoreType getType() {
        return type;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void changeInfo(String name, String typeStr, String vendorCode) {
        this.name = name;
        this.type = StoreType.of(typeStr);
        this.vendorCode = vendorCode;
    }

    public static Store.StoreBuilder builder() {
        return new Store.StoreBuilder();
    }

    public static class StoreBuilder {
        private Long id;

        private String name;

        private StoreType type;

        private String vendorCode;

        private List<Product> products;

        StoreBuilder() {
        }

        public Store.StoreBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public Store.StoreBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public Store.StoreBuilder type(final StoreType type) {
            this.type = type;
            return this;
        }

        public Store.StoreBuilder vendorCode(final String vendorCode) {
            this.vendorCode = vendorCode;
            return this;
        }

        public Store build() {
            return new Store(this.id, this.name, this.type, this.vendorCode, this.products);
        }
    }
}