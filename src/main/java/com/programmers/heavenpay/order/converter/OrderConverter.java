package com.programmers.heavenpay.order.converter;

import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.order.dto.response.OrderCreateResponse;
import com.programmers.heavenpay.order.dto.response.OrderInfoResponse;
import com.programmers.heavenpay.order.dto.response.OrderUpdateResponse;
import com.programmers.heavenpay.order.entity.Order;
import com.programmers.heavenpay.order.entity.vo.OrderStatus;
import com.programmers.heavenpay.product.entitiy.Product;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {
    public OrderCreateResponse toOrderCreateResponse(Order order) {
        return OrderCreateResponse.builder()
                .id(order.getId())
                .createdAt(order.getCreatedDate())
                .build();
    }

    public Order toOrderEntity(int quantity, Member member, Product product) {
        return Order.builder()
                .quantity(quantity)
                .orderStatus(OrderStatus.PAYMENT)
                .member(member)
                .product(product)
                .build();
    }

    public OrderUpdateResponse toOrderUpdateResponse(Order order) {
        return OrderUpdateResponse.builder()
                .id(order.getId())
                .build();
    }

    public OrderInfoResponse toOrderInfoResponse(Order order) {
        return OrderInfoResponse.builder()
                .id(order.getId())
                .productId(order.getProduct().getId())
                .quantity(order.getQuantity())
                .status(order.getOrderStatus().getOrderStatus())
                .createdAt(order.getCreatedDate())
                .mdifiedAt(order.getModifiedDate())
                .build();
    }
}
