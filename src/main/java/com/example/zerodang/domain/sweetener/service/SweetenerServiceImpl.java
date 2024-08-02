package com.example.zerodang.domain.sweetener.service;

import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.domain.sweetener.entity.Sweetener;
import com.example.zerodang.domain.sweetener.repository.SweetenerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SweetenerServiceImpl implements SweetenerService {
    private final SweetenerRepository sweetenerRepository;

    @Override
    public List<Sweetener> getSweetener_product(Product product) {
        return sweetenerRepository.findByProduct(product);
    }
}
