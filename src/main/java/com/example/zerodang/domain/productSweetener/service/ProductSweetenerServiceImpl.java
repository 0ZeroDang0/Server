package com.example.zerodang.domain.productSweetener.service;

import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.domain.productSweetener.entity.ProductSweetener;
import com.example.zerodang.domain.productSweetener.repository.ProductSweetenerRepository;
import com.example.zerodang.domain.sweetener.entity.Sweetener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProductSweetenerServiceImpl implements ProductSweetenerService {
    private final ProductSweetenerRepository productSweetenerRepository;

    @Override
    public List<Sweetener> getSweetenersByProduct(Product product) {
        return productSweetenerRepository.findByProduct(product)
                .stream()
                .map(ProductSweetener::getSweetener)
                .collect(Collectors.toList());
    }
}
