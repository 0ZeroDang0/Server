package com.example.zerodang.domain.productSweetener.entity;

import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.domain.sweetener.entity.Sweetener;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductSweetener {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productSweetenerId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "sweetener_id", nullable = false)
    private Sweetener sweetener;
}
