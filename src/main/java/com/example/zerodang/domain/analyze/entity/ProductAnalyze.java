package com.example.zerodang.domain.analyze.entity;

import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductAnalyze {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productAnalyzeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
