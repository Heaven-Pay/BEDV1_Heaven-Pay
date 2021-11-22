package com.programmers.heavenpay.payment.controller;

import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.payment.dto.request.PaymentCreateRequest;
import com.programmers.heavenpay.payment.dto.request.PaymentDeleteRequest;
import com.programmers.heavenpay.payment.dto.response.PaymentCreateResponse;
import com.programmers.heavenpay.payment.dto.response.PaymentDeleteResponse;
import com.programmers.heavenpay.payment.service.PaymentService;
import com.programmers.heavenpay.pointWallet.controller.PointWalletController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.HttpMethod;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Api(tags = "Payment")
@RestController
@RequestMapping(value = "/api/v1/payments", produces = MediaTypes.HAL_JSON_VALUE)
public class PaymentController {
    private final PaymentService paymentService;
    private final ResponseConverter responseConverter;

    public PaymentController(PaymentService paymentService, ResponseConverter responseConverter) {
        this.paymentService = paymentService;
        this.responseConverter = responseConverter;
    }

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(PointWalletController.class);
    }

    @ApiOperation("포인트 결제하기, 성공시 생성된 결제 ID 반환")
    @PostMapping(consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> addPayment(@Valid @RequestBody PaymentCreateRequest request) {
        PaymentCreateResponse response = paymentService.create(
                request.getMemberId(),
                request.getStoreId(),
                request.getPointWalletId(),
                request.getPayPoint()
        );

        EntityModel<PaymentCreateResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withSelfRel().withTitle(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.PAYMENT_CREATE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("포인트 결제 취소하기, 성공시 삭제된 결제 정보 데이터 반환")
    @DeleteMapping(value = "/{paymentId}")
    public ResponseEntity<ResponseDto> refund(@Valid @PathVariable Long paymentId, @Valid @RequestBody PaymentDeleteRequest request) {
        PaymentDeleteResponse response = paymentService.delete(paymentId,
                request.getMemberId(),
                request.getStoreId(),
                request.getPointWalletId());

        EntityModel<PaymentDeleteResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.PAYMENT_DELETE_SUCCESS,
                entityModel
        );
    }
}
