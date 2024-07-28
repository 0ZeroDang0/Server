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

    @Column(name = "carbohydrate_g")
    private float carbohydrateG;
    @Column(name = "sugar_g")
    private float sugarG;
    @Column(name = "protein_g")
    private float proteinG;
    @Column(name = "fat_g")
    private float fatG;
    @Column(name = "saturated_fat_g")
    private float saturatedFatG;
    @Column(name = "trans_fat_g")
    private float transFatG;
    @Column(name = "cholesterol_g")
    private float cholesterolG;
    @Column(name = "sodium_g")
    private float sodiumG;
}
