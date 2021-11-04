package com.programmers.heavenpay.giftorder.service;

import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.member.repository.MemberRepository;
import com.programmers.heavenpay.giftorder.converter.GiftOrderConverter;
import com.programmers.heavenpay.giftorder.dto.response.GiftOrderCreateResponse;
import com.programmers.heavenpay.giftorder.dto.response.GiftOrderInfoResponse;
import com.programmers.heavenpay.giftorder.dto.response.GiftOrderUpdateResponse;
import com.programmers.heavenpay.giftorder.entity.GiftOrder;
import com.programmers.heavenpay.giftorder.repository.GiftOrderRepository;
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
    private Long giftOrderId = 3L;
    private String status = "결제완료";

    @InjectMocks
    GiftOrderService giftOrderService;

    @Mock
    GiftOrderRepository giftOrderRepository;

    @Mock
    GiftOrderConverter giftOrderConverter;

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
    GiftOrderCreateResponse giftOrderCreateResponse = GiftOrderCreateResponse.builder().build();

    GiftOrderUpdateResponse giftOrderUpdateResponse = GiftOrderUpdateResponse.builder().build();

    GiftOrderInfoResponse giftOrderInfoResponse = GiftOrderInfoResponse.builder().build();
    // ## end of dto define area ### //

    @Test
    void 주문_신규생성_성공_테스트() {
        // given
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(giftOrderConverter.toGiftOrderEntity(quantity, member, product)).thenReturn(giftOrder);
        when(giftOrderRepository.save(giftOrder)).thenReturn(giftOrderEntity);
        when(giftOrderConverter.toGiftOrderCreateResponse(giftOrderEntity)).thenReturn(giftOrderCreateResponse);

        // when
        giftOrderService.create(quantity, memberId, productId);

        // then
        verify(memberRepository).findById(memberId);
        verify(productRepository).findById(productId);
        verify(giftOrderConverter).toGiftOrderEntity(quantity, member, product);
        verify(giftOrderRepository).save(giftOrder);
        verify(giftOrderConverter).toGiftOrderCreateResponse(giftOrderEntity);
    }

    @Test
    void 주문_수정_성공_테스트(){
        // given
        when(giftOrderRepository.findById(giftOrderId)).thenReturn(Optional.of(giftOrder));
        when(giftOrderConverter.toGiftOrderUpdateResponse(giftOrder)).thenReturn(giftOrderUpdateResponse);

        // when
        giftOrderService.update(giftOrderId, quantity, status);

        // then
        verify(giftOrderRepository).findById(giftOrderId);
        verify(giftOrderConverter).toGiftOrderUpdateResponse(giftOrder);
    }

    @Test
    void 주문ID로_조회_성공회테스트(){
        // given
        when(giftOrderRepository.findById(giftOrderId)).thenReturn(Optional.of(giftOrder));
        when(giftOrderConverter.toGiftOrderInfoResponse(giftOrder)).thenReturn(giftOrderInfoResponse);

        // when
        giftOrderService.findById(giftOrderId);

        // then
        verify(giftOrderRepository).findById(giftOrderId);
        verify(giftOrderConverter).toGiftOrderInfoResponse(giftOrder);
    }

    @Test
    void 특정사용자의_모든_주문_조회_성공_테스트(){
        //TODO: 구현
    }

}