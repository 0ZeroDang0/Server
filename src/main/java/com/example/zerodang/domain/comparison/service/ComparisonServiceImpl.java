package com.example.zerodang.domain.comparison.service;

import com.example.zerodang.domain.comparison.entity.Comparison;
import com.example.zerodang.domain.comparison.repository.ComparisonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ComparisonServiceImpl implements ComparisonService {
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
}
