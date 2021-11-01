package com.programmers.heavenpay.product.service;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import com.programmers.heavenpay.product.converter.ProductConverter;
import com.programmers.heavenpay.product.dto.response.ProductCreateResponse;
import com.programmers.heavenpay.product.dto.response.ProductDeleteResponse;
import com.programmers.heavenpay.product.dto.response.ProductInfoResponse;
import com.programmers.heavenpay.product.dto.response.ProductUpdateResponse;
import com.programmers.heavenpay.product.entitiy.Product;
import com.programmers.heavenpay.product.repository.ProductRepository;
import com.programmers.heavenpay.s3.S3Uploader;
import com.programmers.heavenpay.store.entity.Store;
import com.programmers.heavenpay.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final StoreRepository storeRepository;
    private final S3Uploader s3Uploader;

    private final static String s3Dir = "product";
    private final static String EMPTY_URL = "EMPTY_URL";

    @Transactional
    public ProductCreateResponse create(Long storeId, String categoryStr, int price, String title, String description, int stock, MultipartFile multipartFile) throws IOException {
        Optional<MultipartFile> multipartFileOptional = Optional.of(multipartFile);

        Product product = productConverter.toProductEntity(categoryStr, price, title, description, stock);
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_STORE));

        String savedS3Url = EMPTY_URL;
        if(multipartFileOptional.isPresent() && !multipartFileOptional.get().isEmpty()) {
            savedS3Url = s3Uploader.upload(multipartFile, s3Dir);
        }

        product.updateS3Path(savedS3Url);
        product.makeRelationWithStore(store);
        Product productEntity = productRepository.save(product);

        return productConverter.toProductCreateResponse(productEntity);
    }

    @Transactional
    public ProductUpdateResponse update(Long productId, Long storeId, String categoryStr, int price, String title, String description, int stock, MultipartFile multipartFile) throws IOException {
        Optional<MultipartFile> multipartFileOptional = Optional.of(multipartFile);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_PRODUCT));

        product.checkValidStoreOrElseThrow(storeId);

        String updatedS3Url = EMPTY_URL;
        if(multipartFileOptional.isPresent() && !multipartFileOptional.get().isEmpty()) {
            updatedS3Url = s3Uploader.upload(multipartFile, s3Dir);
        }

        product.updateInfos(description, price, updatedS3Url, title, categoryStr, stock);

        return productConverter.toProductUpdateResponse(product);
    }

    @Transactional(readOnly = true)
    public ProductInfoResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_PRODUCT_ID));

        return productConverter.toProductInfoResponse(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductInfoResponse> findAllByPages(Pageable pageable) {
        Page<Product> storePage = productRepository.findAll(pageable);

        return storePage.map(productConverter::toProductInfoResponse);
    }

    @Transactional
    public ProductDeleteResponse delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_PRODUCT));

        productRepository.delete(product);

        return productConverter.toProductDeleteResponse(id);
    }
}
