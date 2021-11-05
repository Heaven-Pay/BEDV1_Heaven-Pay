package com.programmers.heavenpay.giftorder.entity;

import com.programmers.heavenpay.common.entity.BaseEntity;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.giftorder.entity.vo.GiftOrderStatus;
import com.programmers.heavenpay.product.entitiy.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    public void updateInfos(int quantity, String giftOrderStatus){
        this.quantity = quantity;
        this.giftOrderStatus = GiftOrderStatus.of(giftOrderStatus);
    }
}
