package com.example.zerodang.domain.productSweetener.service;

import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.domain.sweetener.entity.Sweetener;

import java.util.List;

public interface ProductSweetenerService {
    List<Sweetener> getSweetenersByProduct(Product product);
}
