package com.programmers.heavenpay.order.service;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.member.repository.MemberRepository;
import com.programmers.heavenpay.order.converter.OrderConverter;
import com.programmers.heavenpay.order.dto.response.OrderCreateResponse;
import com.programmers.heavenpay.order.dto.response.OrderInfoResponse;
import com.programmers.heavenpay.order.dto.response.OrderUpdateResponse;
import com.programmers.heavenpay.order.entity.GiftOrder;
import com.programmers.heavenpay.order.repository.OrderRepository;
import com.programmers.heavenpay.product.entitiy.Product;
import com.programmers.heavenpay.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderCreateResponse create(int quantity, Long memberId, Long productId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER_ID));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_PRODUCT));

        GiftOrder giftOrder = orderConverter.toOrderEntity(quantity, member, product);
        GiftOrder giftOrderEntity = orderRepository.save(giftOrder);

        return orderConverter.toOrderCreateResponse(giftOrderEntity);
    }

    @Transactional
    public OrderUpdateResponse update(Long orderId, int quantity, String status) {
        GiftOrder giftOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_ORDER));

        giftOrder.updateInfos(quantity, status);

        return orderConverter.toOrderUpdateResponse(giftOrder);
    }

    @Transactional(readOnly = true)
    public OrderInfoResponse findById(Long orderId) {
        GiftOrder giftOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_ORDER));

        return orderConverter.toOrderInfoResponse(giftOrder);
    }

    @Transactional(readOnly = true)
    public Page<OrderInfoResponse> findAllByPages(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER_ID));

        Page<GiftOrder> orderPage = orderRepository.findAllByMember(member, pageable);

        return orderPage.map(orderConverter::toOrderInfoResponse);
    }
}
