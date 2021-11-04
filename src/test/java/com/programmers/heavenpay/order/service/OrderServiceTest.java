package com.programmers.heavenpay.order.service;

import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.member.repository.MemberRepository;
import com.programmers.heavenpay.order.converter.OrderConverter;
import com.programmers.heavenpay.order.dto.response.OrderCreateResponse;
import com.programmers.heavenpay.order.dto.response.OrderInfoResponse;
import com.programmers.heavenpay.order.dto.response.OrderUpdateResponse;
import com.programmers.heavenpay.order.entity.Order;
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
class OrderServiceTest {
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
    Page<Order> orderPage;

    Member member = Member.builder().build();

    Product product = Product.builder().build();

    Order order = Order.builder().build();

    Order orderEntity = Order.builder().build();

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
        when(orderConverter.toOrderEntity(quantity, member, product)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(orderEntity);
        when(orderConverter.toOrderCreateResponse(orderEntity)).thenReturn(orderCreateResponse);

        // when
        orderService.create(quantity, memberId, productId);

        // then
        verify(memberRepository).findById(memberId);
        verify(productRepository).findById(productId);
        verify(orderConverter).toOrderEntity(quantity, member, product);
        verify(orderRepository).save(order);
        verify(orderConverter).toOrderCreateResponse(orderEntity);
    }

    @Test
    void 주문_수정_성공_테스트(){
        // given
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderConverter.toOrderUpdateResponse(order)).thenReturn(orderUpdateResponse);

        // when
        orderService.update(orderId, quantity, status);

        // then
        verify(orderRepository).findById(orderId);
        verify(orderConverter).toOrderUpdateResponse(order);
    }

    @Test
    void 주문ID로_조회_성공회테스트(){
        // given
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderConverter.toOrderInfoResponse(order)).thenReturn(orderInfoResponse);

        // when
        orderService.findById(orderId);

        // then
        verify(orderRepository).findById(orderId);
        verify(orderConverter).toOrderInfoResponse(order);
    }

    @Test
    void 특정사용자의_모든_주문_조회_성공_테스트(){
        //TODO: 구현
    }

}