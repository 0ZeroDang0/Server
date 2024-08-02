package com.example.zerodang.domain.productAnalyze.service;

import com.example.zerodang.domain.count.service.CountService;
import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.domain.product.service.ProductService;
import com.example.zerodang.domain.productAnalyze.dto.request.ProductAnalyzeRequestDTO;
import com.example.zerodang.domain.productAnalyze.dto.response.ProductAnalyzeResponseDTO;
import com.example.zerodang.domain.productAnalyze.entity.ProductAnalyze;
import com.example.zerodang.domain.productAnalyze.mapper.ProductAnalyzeMapper;
import com.example.zerodang.domain.productAnalyze.repository.ProductAnalyzeRepository;
import com.example.zerodang.domain.productSweetener.service.ProductSweetenerService;
import com.example.zerodang.domain.sweetener.entity.Sweetener;
import com.example.zerodang.domain.user.entity.User;
import com.example.zerodang.domain.user.service.UserService;
import com.example.zerodang.global.exception.productAnalyze.ProductAnalyzeNotFoundException;
import com.example.zerodang.global.gpt.service.GptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static com.example.zerodang.global.exception.ErrorCode.NOT_FOUND_PRODUCT_ANALYZE;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProductAnalyzeServiceImpl implements ProductAnalyzeService {
    private final ProductAnalyzeRepository productAnalyzeRepository;
    private final ProductService productService;
    private final ProductSweetenerService productSweetenerService;
    private final GptService gptService;
    private final UserService userService;
    private final CountService countService;
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

    @Override
    public ProductAnalyzeResponseDTO.ProductAnalyzeResultDTO result(ProductAnalyzeRequestDTO.ProductAnalyzeComparisonDTO productAnalyzeComparisonDTO) {
        Product product1 = productService.getProduct_id(productAnalyzeComparisonDTO.getProductId1());
        Product product2 = productService.getProduct_id(productAnalyzeComparisonDTO.getProductId2());
        String resultComment = gptService.analyzeWithGPT(product1, product2).block(); // 영양 분석 결과
        List<Sweetener> sweetenerProduct1 = productSweetenerService.getSweetenersByProduct(product1);
        List<Sweetener> sweetenerProduct2 = productSweetenerService.getSweetenersByProduct(product2);
        List<Sweetener> sweetenerList = getSweetenerList(sweetenerProduct1, sweetenerProduct2);
//        countService.recordComparison(); // 비교 횟수 증가

        return productAnalyzeMapper.toProductAnalyzeResultDTO(product1, product2, sweetenerList, resultComment);
    }

    private static List<Sweetener> getSweetenerList(List<Sweetener> sweetenerProduct1, List<Sweetener> sweetenerProduct2) {
        Set<Sweetener> sweetenerSet = new HashSet<>();
        sweetenerProduct1.stream()
                .filter(sweetener1 -> sweetenerProduct2.stream()
                        .anyMatch(sweetener2 -> sweetener1.getSweetenerName().equals(sweetener2.getSweetenerName())))
                .forEach(sweetenerSet::add);
        return new ArrayList<>(sweetenerSet);
    }

    public ProductAnalyze getProductAnalyze_id(Long productAnalyzeId) {
        return productAnalyzeRepository.findById(productAnalyzeId)
                .orElseThrow(() -> new ProductAnalyzeNotFoundException(NOT_FOUND_PRODUCT_ANALYZE));
    }
}
