package com.example.zerodang.domain.sweetener.entity;

import com.example.zerodang.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sweetener {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sweetenerId;

    @Enumerated(EnumType.STRING)
    private SweetenerStatus sweetenerStatus;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
