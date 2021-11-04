package com.programmers.heavenpay.order.converter;

import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.order.dto.response.OrderCreateResponse;
import com.programmers.heavenpay.order.dto.response.OrderInfoResponse;
import com.programmers.heavenpay.order.dto.response.OrderUpdateResponse;
import com.programmers.heavenpay.order.entity.GiftOrder;
import com.programmers.heavenpay.order.entity.vo.OrderStatus;
import com.programmers.heavenpay.product.entitiy.Product;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {
    public OrderCreateResponse toOrderCreateResponse(GiftOrder giftOrder) {
        return OrderCreateResponse.builder()
                .id(giftOrder.getId())
                .createdAt(giftOrder.getCreatedDate())
                .build();
    }

    public GiftOrder toOrderEntity(int quantity, Member member, Product product) {
        return GiftOrder.builder()
                .quantity(quantity)
                .orderStatus(OrderStatus.PAYMENT)
                .member(member)
                .product(product)
                .build();
    }

    public OrderUpdateResponse toOrderUpdateResponse(GiftOrder giftOrder) {
        return OrderUpdateResponse.builder()
                .id(giftOrder.getId())
                .build();
    }

    public OrderInfoResponse toOrderInfoResponse(GiftOrder giftOrder) {
        return OrderInfoResponse.builder()
                .id(giftOrder.getId())
                .productId(giftOrder.getProduct().getId())
                .quantity(giftOrder.getQuantity())
                .status(giftOrder.getOrderStatus().getOrderStatus())
                .createdAt(giftOrder.getCreatedDate())
                .mdifiedAt(giftOrder.getModifiedDate())
                .build();
    }
}
