package com.programmers.heavenpay.payment.entity;

import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.pointWallet.entity.PointWallet;
import com.programmers.heavenpay.store.entity.Store;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    public void updateData(Integer paymentPoint) {
        this.paymentPoint = paymentPoint;
    }
}
