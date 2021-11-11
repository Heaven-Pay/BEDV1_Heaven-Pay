package com.programmers.heavenpay.product.service;

import com.programmers.heavenpay.product.converter.ProductConverter;
import com.programmers.heavenpay.product.dto.response.ProductCreateResponse;
import com.programmers.heavenpay.product.dto.response.ProductDeleteResponse;
import com.programmers.heavenpay.product.dto.response.ProductInfoResponse;
import com.programmers.heavenpay.product.entitiy.Product;
import com.programmers.heavenpay.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    Long id = 1L;
    Long storeId = 1L;
    String categoryStr = "전자제품";
    int price = 4000;
    String title = "title1";
    String description = "description1";
    int stock = 4;
    MultipartFile multipartFile;

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductConverter productConverter;

    @Mock
    Pageable pageable;

    @Mock
    Page<Product> productPage;

    Product product = Product.builder().build();

    @Mock
    ProductInfoResponse productInfoResponse;

    ProductDeleteResponse productDeleteResponse;

    ProductCreateResponse productCreateResponse;


    @Test
    @DisplayName("product를 삽입하는 로직이 정상적으로 수행된다.")
    void createSuccessTest() throws IOException {  //TODO: 테스트 통과 못함
        //given
        when(productConverter.toProductEntity(anyString(), anyInt(), anyString(), anyString(), anyInt()))
                .thenReturn(product);
        when(productRepository.save(product))
                .thenReturn(product);
        when(productConverter.toProductCreateResponse(product))
                .thenReturn(productCreateResponse);

        //when
        productService.create(storeId, categoryStr, price, title, description, stock, multipartFile);

        //then
        verify(productConverter).toProductEntity(anyString(), anyInt(), anyString(), anyString(), anyInt());
        verify(productRepository).save(product);
        verify(productConverter).toProductCreateResponse(product);
    }

    @Test
    @DisplayName("findById 로직이 성공적으로 수행된다.")
    void findByIdSuccessTest(){
        when(productRepository.findById(anyLong()))
                .thenReturn(Optional.of(product));
        when(productConverter.toProductInfoResponse(product))
                .thenReturn(productInfoResponse);

        productService.findById(id);

        verify(productRepository).findById(anyLong());
        verify(productConverter).toProductInfoResponse(product);
    }

    @Test
    @DisplayName("find all 로직이 정상적으로 수행된다.")
    void finalAllSuccessTest(){
        // given
        when(productRepository.findAll(pageable)).thenReturn(productPage);

        // when
        productService.findAllByPages(pageable);

        // then
        verify(productRepository).findAll(pageable);
    }

    @Test
    @DisplayName("delete 로직이 정상적으로 수행된다.")
    void deleteSuccessTest(){
        when(productRepository.findById(anyLong()))
                .thenReturn(Optional.of(product));
        when(productConverter.toProductDeleteResponse(anyLong()))
                .thenReturn(productDeleteResponse);

        productService.delete(anyLong());

        verify(productRepository).findById(anyLong());
        verify(productConverter).toProductDeleteResponse(anyLong());
    }
}