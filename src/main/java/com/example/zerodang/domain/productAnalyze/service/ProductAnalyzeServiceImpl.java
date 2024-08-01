package com.example.zerodang.domain.productAnalyze.service;

import com.example.zerodang.domain.product.service.ProductService;
import com.example.zerodang.domain.productAnalyze.dto.request.ProductAnalyzeRequestDTO;
import com.example.zerodang.domain.productAnalyze.dto.response.ProductAnalyzeResponseDTO;
import com.example.zerodang.domain.productAnalyze.entity.ProductAnalyze;
import com.example.zerodang.domain.productAnalyze.mapper.ProductAnalyzeMapper;
import com.example.zerodang.domain.productAnalyze.repository.ProductAnalyzeRepository;
import com.example.zerodang.domain.user.entity.User;
import com.example.zerodang.domain.user.service.UserService;
import com.example.zerodang.global.exception.productAnalyze.ProductAnalyzeNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.zerodang.global.exception.ErrorCode.NOT_FOUND_PRODUCT_ANALYZE;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProductAnalyzeServiceImpl implements ProductAnalyzeService {
    private final ProductAnalyzeRepository productAnalyzeRepository;
    private final ProductService productService;
    private final UserService userService;
    private final ProductAnalyzeMapper productAnalyzeMapper;

    @Override
    @Transactional
    public ProductAnalyzeResponseDTO.ProductAnalyzeSaveDTO cart(Long productId, Long userId) {
        ProductAnalyze productAnalyze = productAnalyzeMapper.toProductAnalyzeEntity(productService.getProduct_id(productId), userService.getUser_Id(userId));
        productAnalyzeRepository.save(productAnalyze);
        return productAnalyzeMapper.toProductAnalyzeSaveResDTO(productAnalyze);
    }

    @Override
    public Page<ProductAnalyzeResponseDTO.ProductAnalyzeFindOneDTO> findAllByUserId(Long userId, Pageable pageable) {
        return productAnalyzeRepository.findAllByUserIdWithPageable(userId, pageable);
    }


    @Transactional
    @Override
    public void delete(ProductAnalyzeRequestDTO.ProductAnalyzeDeleteDTO productAnalyzeDeleteDTO, Long userId) {
        User findUser = userService.getUser_Id(userId);

        List<Long> productAnalyzeIds = productAnalyzeDeleteDTO.getProductAnalyzeLists();

        for (Long productAnalyzeId : productAnalyzeIds) {
            ProductAnalyze findProductAnalyze = getProductAnalyze_id(productAnalyzeId);
            if (findProductAnalyze.getUser().equals(findUser)) {
                productAnalyzeRepository.deleteById(productAnalyzeId);
            }
        }
    }

    public ProductAnalyze getProductAnalyze_id(Long productAnalyzeId) {
        return productAnalyzeRepository.findById(productAnalyzeId)
                .orElseThrow(() -> new ProductAnalyzeNotFoundException(NOT_FOUND_PRODUCT_ANALYZE));
    }
}
