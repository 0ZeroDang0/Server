package com.example.zerodang.domain.sweetener.service;

import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.domain.sweetener.entity.Sweetener;

import java.util.List;

public interface SweetenerService {
    List<Sweetener> getSweetener_product(Product product);
}
