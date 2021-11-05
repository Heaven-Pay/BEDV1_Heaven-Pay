package com.programmers.heavenpay.giftorder.service;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GiftOrderService {
    private final GiftOrderRepository giftOrderRepository;
    private final GiftOrderConverter giftOrderConverter;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional
    public GiftOrderCreateResponse create(int quantity, Long memberId, Long productId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER_ID));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_PRODUCT));

        GiftOrder giftOrder = giftOrderConverter.toGiftOrderEntity(quantity, member, product);
        GiftOrder giftOrderEntity = giftOrderRepository.save(giftOrder);

        return giftOrderConverter.toGiftOrderCreateResponse(giftOrderEntity);
    }

    @Transactional
    public GiftOrderUpdateResponse update(Long giftOrderId, int quantity, String status) {
        GiftOrder giftOrder = giftOrderRepository.findById(giftOrderId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_GIFT_ORDER));

        giftOrder.updateInfos(quantity, status);

        return giftOrderConverter.toGiftOrderUpdateResponse(giftOrder);
    }

    @Transactional(readOnly = true)
    public GiftOrderInfoResponse findById(Long giftOrderId) {
        GiftOrder giftOrder = giftOrderRepository.findById(giftOrderId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_GIFT_ORDER));

        return giftOrderConverter.toGiftOrderInfoResponse(giftOrder);
    }

    @Transactional(readOnly = true)
    public Page<GiftOrderInfoResponse> findAllByPages(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER_ID));

        Page<GiftOrder> giftOrderPage = giftOrderRepository.findAllByMember(member, pageable);

        return giftOrderPage.map(giftOrderConverter::toGiftOrderInfoResponse);
    }
}
