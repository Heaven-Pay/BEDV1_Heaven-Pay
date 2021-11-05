package com.programmers.heavenpay.product.controller;

import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.product.dto.request.ProductCreateRequest;
import com.programmers.heavenpay.product.dto.request.ProductUpdateRequest;
import com.programmers.heavenpay.product.dto.response.ProductCreateResponse;
import com.programmers.heavenpay.product.dto.response.ProductDeleteResponse;
import com.programmers.heavenpay.product.dto.response.ProductInfoResponse;
import com.programmers.heavenpay.product.dto.response.ProductUpdateResponse;
import com.programmers.heavenpay.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/products", produces = MediaTypes.HAL_JSON_VALUE)
@Api(tags = "Product")
public class ProductController {
    private final ProductService productService;
    private final ResponseConverter responseConverter;

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(ProductController.class);
    }

    @ApiOperation("상품 등록")
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ResponseDto> insert(@Valid @ModelAttribute ProductCreateRequest request) throws IOException {
        ProductCreateResponse response = productService.create(
                request.getStoreID(),
                request.getCategory(),
                request.getPrice(),
                request.getTitle(),
                request.getDescription(),
                request.getStock(),
                request.getMultipartFile()
        );

        EntityModel<ProductCreateResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.PRODUCT_INSERT_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("상품 단건 조회")
    @GetMapping("/{productId}")
    public ResponseEntity<ResponseDto> getOne(@Valid @PathVariable Long productId) {
        ProductInfoResponse response = productService.findById(productId);

        EntityModel<ProductInfoResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.PRODUCT_SEARCH_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("모든 상품 조회")
    @GetMapping()
    public ResponseEntity<ResponseDto> getAll(Pageable pageable) {
        Page<ProductInfoResponse> responses = productService.findAllByPages(pageable);

        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        return responseConverter.toResponseEntity(
                ResponseMessage.PRODUCT_SEARCH_SUCCESS,
                responses,
                link
        );
    }

    @ApiOperation("상품 단건 삭제")
    @DeleteMapping(value = "/{productId}")
    public ResponseEntity<ResponseDto> delete(@Valid @PathVariable Long productId) {
        ProductDeleteResponse response = productService.delete(productId);

        EntityModel<ProductDeleteResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.PRODUCT_DELETE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("상품 정보 수정")
    @PatchMapping(value = "/{productId}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> update(@PathVariable Long productId, @Valid @RequestBody ProductUpdateRequest request) throws IOException {
        ProductUpdateResponse response = productService.update(
                productId,
                request.getStoreID(),
                request.getCategory(),
                request.getPrice(),
                request.getTitle(),
                request.getDescription(),
                request.getStock(),
                request.getMultipartFile()
        );

        EntityModel<ProductUpdateResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return responseConverter.toResponseEntity(
                ResponseMessage.PRODUCT_UPDATE_SUCCESS,
                entityModel
        );
    }
}
