package com.example.zerodang.domain.product.entity;

import com.example.zerodang.global.entity.BaseTimeEntity;
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
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private int productMl;

    private String productDescription;
    private float carbohydrateG;
    private float sugarG;
    private float proteinG;
    private float fatG;
    private float saturatedFatG;
    private float transFatG;
    private float cholesterolG;
    private float sodiumG;
}
