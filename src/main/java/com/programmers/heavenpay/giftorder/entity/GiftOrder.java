package com.programmers.heavenpay.giftorder.entity;

import com.programmers.heavenpay.common.entity.BaseEntity;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.giftorder.entity.vo.GiftOrderStatus;
import com.programmers.heavenpay.product.entitiy.Product;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gift_order")
public class GiftOrder extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gift_order_id", unique = true)
    private Long id;

    @Column(name = "gift_order_quantity", nullable = false)
    private int quantity;

    @Column(name = "gift_order_status")
    @Enumerated(EnumType.STRING)
    private GiftOrderStatus giftOrderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tMember_id", referencedColumnName = "member_id", nullable = false)
    private Member tMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Product product;

    protected GiftOrder() {
    }

    public GiftOrder(Long id, int quantity, GiftOrderStatus giftOrderStatus, Member member, Member tMember, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.giftOrderStatus = giftOrderStatus;
        this.member = member;
        this.tMember = tMember;
        this.product = product;
    }

    public void updateInfos(int quantity, String giftOrderStatus) {
        this.quantity = quantity;
        this.giftOrderStatus = GiftOrderStatus.of(giftOrderStatus);
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public GiftOrderStatus getGiftOrderStatus() {
        return giftOrderStatus;
    }

    public Member getMember() {
        return member;
    }

    public Member gettMember() {
        return tMember;
    }

    public Product getProduct() {
        return product;
    }

    public static GiftOrder.GiftOrderBuilder builder() {
        return new GiftOrder.GiftOrderBuilder();
    }

    public static class GiftOrderBuilder {
        private Long id;

        private int quantity;

        private GiftOrderStatus giftOrderStatus;

        private Member member;

        private Member tMember;

        private Product product;

        protected LocalDateTime createdAt;

        protected LocalDateTime modifiedAt;

        GiftOrderBuilder() {
        }

        public GiftOrder.GiftOrderBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public GiftOrder.GiftOrderBuilder quantity(final int quantity) {
            this.quantity = quantity;
            return this;
        }

        public GiftOrder.GiftOrderBuilder giftOrderStatus(final GiftOrderStatus giftOrderStatus) {
            this.giftOrderStatus = giftOrderStatus;
            return this;
        }

        public GiftOrder.GiftOrderBuilder member(final Member member) {
            this.member = member;
            return this;
        }

        public GiftOrder.GiftOrderBuilder tMember(final Member tMember) {
            this.tMember = tMember;
            return this;
        }

        public GiftOrder.GiftOrderBuilder product(final Product product) {
            this.product = product;
            return this;
        }

        public GiftOrder.GiftOrderBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public GiftOrder.GiftOrderBuilder modifiedAt(final LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public GiftOrder build() {
            return new GiftOrder(
                    id,
                    quantity,
                    giftOrderStatus,
                    member,
                    tMember,
                    product
            );
        }
    }
}
