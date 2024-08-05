package com.example.zerodang.domain.comparison.service;

import com.example.zerodang.domain.comparison.dto.response.ComparisonResponseDTO;
import com.example.zerodang.domain.comparison.entity.Comparison;
import com.example.zerodang.domain.comparison.repository.ComparisonRepository;
import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ComparisonServiceImpl implements ComparisonService {
    private final ProductService productService;
    private final ComparisonRepository comparisonRepository;

    @Override
    @Transactional
    public void comparison(Long product1, Long product2) {
        Long sortedProduct1 = Math.min(product1, product2);
        Long sortedProduct2 = Math.max(product1, product2);

        Optional<Comparison> optionalComparison = comparisonRepository.findByProduct1AndProduct2(sortedProduct1, sortedProduct2);

        if(!optionalComparison.isPresent()) {
            comparisonRepository.save(Comparison.builder().product1(sortedProduct1).product2(sortedProduct2).views(1).build());
        } else {
            optionalComparison.get().plusViews();
        }
    }

    @Override
    public ComparisonResponseDTO.ComparisonTOP3DTO findAllByTOP3() {
        List<Comparison> top3Comparisons = comparisonRepository.findTop3ByOrderByViewsDesc();
        List<ComparisonResponseDTO.ComparisonDetailDTO> comparisonDetailDTOList = top3Comparisons.stream()
                .map(comparison -> {
                    Product product1 = productService.getProduct_id(comparison.getProduct1());
                    Product product2 = productService.getProduct_id(comparison.getProduct2());
                    return ComparisonResponseDTO.ComparisonDetailDTO.builder()
                            .productId1(product1.getProductId())
                            .productName1(product1.getProductName())
                            .thumbnail1(product1.getThumbnail())
                            .productId2(product2.getProductId())
                            .productName2(product2.getProductName())
                            .thumbnail2(product2.getThumbnail())
                            .build();
                })
                .collect(Collectors.toList());

        return ComparisonResponseDTO.ComparisonTOP3DTO.builder()
                .comparisonDetailDTOList(comparisonDetailDTOList)
                .build();
    }
}
