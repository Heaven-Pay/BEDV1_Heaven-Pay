package com.programmers.heavenpay.payment.entity;

import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.pointWallet.entity.PointWallet;
import com.programmers.heavenpay.store.entity.Store;

import javax.persistence.*;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "payment_id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_wallet_id")
    private PointWallet pointWallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "payment_point", nullable = false)
    private Integer paymentPoint;

    protected Payment() {
    }

    public Payment(Long id, Member member, PointWallet pointWallet, Store store, Integer paymentPoint) {
        this.id = id;
        this.member = member;
        this.pointWallet = pointWallet;
        this.store = store;
        this.paymentPoint = paymentPoint;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public PointWallet getPointWallet() {
        return pointWallet;
    }

    public Store getStore() {
        return store;
    }

    public Integer getPaymentPoint() {
        return paymentPoint;
    }

    public void updateData(Integer paymentPoint) {
        this.paymentPoint = paymentPoint;
    }

    public static Payment.PaymentBuilder builder() {
        return new Payment.PaymentBuilder();
    }

    public static class PaymentBuilder {
        private Long id;
        private Member member;
        private PointWallet pointWallet;
        private Store store;
        private Integer paymentPoint;

        private PaymentBuilder() {
        }

        public Payment.PaymentBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public Payment.PaymentBuilder member(final Member member) {
            this.member = member;
            return this;
        }

        public Payment.PaymentBuilder pointWallet(final PointWallet pointWallet) {
            this.pointWallet = pointWallet;
            return this;
        }

        public Payment.PaymentBuilder store(final Store store) {
            this.store = store;
            return this;
        }

        public Payment.PaymentBuilder paymentPoint(final Integer paymentPoint) {
            this.paymentPoint = paymentPoint;
            return this;
        }

        public Payment build() {
            return new Payment(this.id, this.member, this.pointWallet, this.store, this.paymentPoint);
        }
    }
}
