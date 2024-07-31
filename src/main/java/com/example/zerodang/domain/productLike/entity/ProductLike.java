package com.example.zerodang.domain.productLike.entity;

import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.domain.user.entity.User;
import com.example.zerodang.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductLike extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productLikeId;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
