package com.programmers.heavenpay.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.heavenpay.common.converter.ResponseConverter;
import com.programmers.heavenpay.common.dto.LinkType;
import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.product.dto.request.ProductCreateRequest;
import com.programmers.heavenpay.product.dto.response.ProductCreateResponse;
import com.programmers.heavenpay.product.dto.response.ProductDeleteResponse;
import com.programmers.heavenpay.product.dto.response.ProductInfoResponse;
import com.programmers.heavenpay.product.dto.response.ProductUpdateResponse;
import com.programmers.heavenpay.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {
    private Long id = 2L;
    private String category = "전자제품";
    private Long storeId = 1L;
    private int price = 4000;
    private String title = "title123";
    private String description = "descreiption123";
    private int stock = 2;
    private MultipartFile multipartFile;

//    MockMultipartFile multipartFile
//            = new MockMultipartFile(
//            "file",
//            "hello.txt",
//            MediaType.TEXT_PLAIN_VALUE,
//            "Hello, World!".getBytes()
//    );

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private ResponseConverter responseConverter;

    @MockBean
    private Pageable pageable;

    @MockBean
    private Page<ProductInfoResponse> productPage;

    private ProductCreateResponse productCreateResponse = ProductCreateResponse.builder().build();
    private ProductDeleteResponse productDeleteResponse = ProductDeleteResponse.builder().build();
    private ProductUpdateResponse productUpdateResponse = ProductUpdateResponse.builder().build();
    private ProductInfoResponse productInfoResponse = ProductInfoResponse.builder().build();

    @Test
    @DisplayName("Product 생성 성공 테스트")   //TODO: 테스트 통과 못함. actual status : 400
    void insertSuccessTest() throws Exception {
        //given
        ProductCreateRequest productCreateRequest = ProductCreateRequest.builder()
                .category(category)
                .price(price)
                .description(description)
                .multipartFile(multipartFile)
                .stock(stock)
                .storeID(storeId)
                .title(title)
                .build();

        EntityModel<ProductCreateResponse> entityModel = EntityModel.of(
                productCreateResponse,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(id).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(id).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(id).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        // When
        when(productService.create(anyLong(), anyString(), anyInt(), anyString(), anyString(), anyInt(), any()))
                .thenReturn(productCreateResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.PRODUCT_INSERT_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.PRODUCT_INSERT_SUCCESS, entityModel)));

        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/products");
        requestBuilder.contentType("multipart/form-data"); // request 헤더정보를 맞추기
        requestBuilder.content(objectMapper.writeValueAsString(productCreateRequest));
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE); // 응답 받을 타입을 명시 (클라이언트가 읽을 수 있는 형식)

        // Then
        mockMvc.perform(requestBuilder)  // 컨트롤러 실행
                .andDo(print())  // 결과 출력
                .andExpect(status().isOk());   // 응답 결과 검증
    }

    @Test
    @DisplayName("patch 요청으로 product 를 수정할 수 있다.")
    void patchSuccesstest(){
        //TODO: insert 테스트 성공 후 작성
    }

    @Test
    @DisplayName("delete 요청으로 product 삭제 요청을 수행할 수 있다.")
    void deldeteSuccessTest() throws Exception {
        //given
        EntityModel<ProductDeleteResponse> entityModel = EntityModel.of(
                productDeleteResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(id).withSelfRel().withType(HttpMethod.DELETE.name())
        );

        //when
        when(productService.delete(anyLong()))
                .thenReturn(productDeleteResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.PRODUCT_DELETE_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.PRODUCT_DELETE_SUCCESS, entityModel)));

        MockHttpServletRequestBuilder requestBuilder = delete("/api/v1/products/{productId}", anyLong());
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        //then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("get 요청으로 Product 단건을 조회할 수 있다.")
    void getOneSuccessTest() throws Exception {
        //given
        EntityModel<ProductInfoResponse> entityModel = EntityModel.of(
                productInfoResponse,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(id).withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(id).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(id).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        //when
        when(productService.findById(anyLong()))
                .thenReturn(productInfoResponse);
        when(responseConverter.toResponseEntity(ResponseMessage.PRODUCT_SEARCH_SUCCESS, entityModel))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.PRODUCT_SEARCH_SUCCESS, entityModel)));

        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/products/{productId}", anyLong());
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        //then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("get 요청으로 모든 products를 조회할 수 있다.")
    void getAllSuccessTest() throws Exception {
        // Given
        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        // When
        when(productService.findAllByPages(pageable))
                .thenReturn(productPage);
        when(responseConverter.toResponseEntity(HttpStatus.OK, ResponseMessage.PRODUCT_SEARCH_SUCCESS, productPage, link))
                .thenReturn(ResponseEntity.ok(ResponseDto.of(ResponseMessage.PRODUCT_SEARCH_SUCCESS, productPage, link)));

        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/products");
        requestBuilder.contentType(MediaTypes.HAL_JSON_VALUE);
        requestBuilder.accept(MediaTypes.HAL_JSON_VALUE);

        // Then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(ProductController.class);
    }
}