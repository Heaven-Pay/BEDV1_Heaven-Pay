package com.programmers.heavenpay.product.service;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import com.programmers.heavenpay.product.converter.ProductConverter;
import com.programmers.heavenpay.product.dto.response.ProductCreateResponse;
import com.programmers.heavenpay.product.dto.response.ProductUpdateResponse;
import com.programmers.heavenpay.product.entitiy.Product;
import com.programmers.heavenpay.product.repository.ProductRepository;
import com.programmers.heavenpay.s3.S3Uploader;
import com.programmers.heavenpay.store.entity.Store;
import com.programmers.heavenpay.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final StoreRepository storeRepository;
    private final S3Uploader s3Uploader;

    private final static String s3Dir = "product";
    private final static String EMPTY = "EMPTY";

    @Transactional
    public ProductCreateResponse create(Long storeId, String categoryStr, int price, String title, String description, int stock, MultipartFile multipartFile) throws IOException {
        Product product = productConverter.toProductEntity(categoryStr, price, title, description, stock);
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_STORE));

        String savedS3Url = multipartFile.isEmpty() ? EMPTY : s3Uploader.upload(multipartFile, s3Dir);

        product.updateS3Path(savedS3Url);
        product.makeRelationWithStore(store);
        Product productEntity = productRepository.save(product);

        return productConverter.toProductCreateResponse(
                productEntity.getId(),
                productEntity.getCreatedDate(),
                productEntity.getS3Path()
        );
    }

    @Transactional
    public ProductUpdateResponse update(Long productId, Long storeId, String categoryStr, int price, String title, String description, int stock, MultipartFile multipartFile) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_PRODUCT));

        product.checkValidStoreOrElseThrow(storeId);

        String updatedS3Url = multipartFile.isEmpty() ? EMPTY : s3Uploader.upload(multipartFile, s3Dir);

        product.updateInfos(description, price, updatedS3Url, title, categoryStr, stock);

        return ProductUpdateResponse.builder()
                .id(productId)
                .createdAt(product.getCreatedDate())
                .modifiedAt(product.getModifiedDate())
                .s3Path(updatedS3Url)
                .build();
    }
}
