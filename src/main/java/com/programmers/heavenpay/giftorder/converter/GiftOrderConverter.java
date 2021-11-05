package com.programmers.heavenpay.giftorder.converter;

import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.giftorder.dto.response.GiftOrderCreateResponse;
import com.programmers.heavenpay.giftorder.dto.response.GiftOrderInfoResponse;
import com.programmers.heavenpay.giftorder.dto.response.GiftOrderUpdateResponse;
import com.programmers.heavenpay.giftorder.entity.GiftOrder;
import com.programmers.heavenpay.giftorder.entity.vo.GiftOrderStatus;
import com.programmers.heavenpay.product.entitiy.Product;
import org.springframework.stereotype.Component;

@Component
public class GiftOrderConverter {
    public GiftOrderCreateResponse toGiftOrderCreateResponse(GiftOrder giftOrder) {
        return GiftOrderCreateResponse.builder()
                .id(giftOrder.getId())
                .createdAt(giftOrder.getCreatedDate())
                .build();
    }

    public GiftOrder toGiftOrderEntity(int quantity, Member member, Member targetMember, Product product) {
        return GiftOrder.builder()
                .quantity(quantity)
                .giftOrderStatus(GiftOrderStatus.PAYMENT)
                .member(member)
                .product(product)
                .tMember(targetMember)
                .build();
    }

    public GiftOrderUpdateResponse toGiftOrderUpdateResponse(GiftOrder giftOrder) {
        return GiftOrderUpdateResponse.builder()
                .id(giftOrder.getId())
                .build();
    }

    public GiftOrderInfoResponse toGiftOrderInfoResponse(GiftOrder giftOrder) {
        return GiftOrderInfoResponse.builder()
                .id(giftOrder.getId())
                .productId(giftOrder.getProduct().getId())
                .quantity(giftOrder.getQuantity())
                .status(giftOrder.getGiftOrderStatus().getGiftOrderStatus())
                .createdAt(giftOrder.getCreatedDate())
                .mdifiedAt(giftOrder.getModifiedDate())
                .build();
    }
}
