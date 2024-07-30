package com.example.zerodang.domain.sweetener.entity;

import com.example.zerodang.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sweetener {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sweetenerId;

    private String sweetenerDescription;

    @Enumerated(EnumType.STRING)
    private SweetenerStatus sweetenerStatus;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
