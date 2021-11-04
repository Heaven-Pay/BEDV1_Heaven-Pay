package com.programmers.heavenpay.order.service;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftOrderServiceTest {
    private Long memberId = 1L;
    private Long productId = 2L;
    private int quantity = 3;
    private Long orderId = 3L;
    private String status = "결제완료";

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderConverter orderConverter;

    @Mock
    MemberRepository memberRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    Pageable pageable;

    @Mock
    Page<GiftOrder> orderPage;

    Member member = Member.builder().build();

    Product product = Product.builder().build();

    GiftOrder giftOrder = GiftOrder.builder().build();

    GiftOrder giftOrderEntity = GiftOrder.builder().build();

    // ## dto define area ### //
    OrderCreateResponse orderCreateResponse = OrderCreateResponse.builder().build();

    OrderUpdateResponse orderUpdateResponse = OrderUpdateResponse.builder().build();

    OrderInfoResponse orderInfoResponse = OrderInfoResponse.builder().build();
    // ## end of dto define area ### //

    @Test
    void 주문_신규생성_성공_테스트() {
        // given
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(orderConverter.toOrderEntity(quantity, member, product)).thenReturn(giftOrder);
        when(orderRepository.save(giftOrder)).thenReturn(giftOrderEntity);
        when(orderConverter.toOrderCreateResponse(giftOrderEntity)).thenReturn(orderCreateResponse);

        // when
        orderService.create(quantity, memberId, productId);

        // then
        verify(memberRepository).findById(memberId);
        verify(productRepository).findById(productId);
        verify(orderConverter).toOrderEntity(quantity, member, product);
        verify(orderRepository).save(giftOrder);
        verify(orderConverter).toOrderCreateResponse(giftOrderEntity);
    }

    @Test
    void 주문_수정_성공_테스트(){
        // given
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(giftOrder));
        when(orderConverter.toOrderUpdateResponse(giftOrder)).thenReturn(orderUpdateResponse);

        // when
        orderService.update(orderId, quantity, status);

        // then
        verify(orderRepository).findById(orderId);
        verify(orderConverter).toOrderUpdateResponse(giftOrder);
    }

    @Test
    void 주문ID로_조회_성공회테스트(){
        // given
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(giftOrder));
        when(orderConverter.toOrderInfoResponse(giftOrder)).thenReturn(orderInfoResponse);

        // when
        orderService.findById(orderId);

        // then
        verify(orderRepository).findById(orderId);
        verify(orderConverter).toOrderInfoResponse(giftOrder);
    }

    @Test
    void 특정사용자의_모든_주문_조회_성공_테스트(){
        //TODO: 구현
    }

}