package com.example.zerodang.domain.product.entity;

import com.example.zerodang.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private int productMl;

    private String productDescription; // 원재료 설명

    @Column(name = "carbohydrate_g")
    private float carbohydrateG; // 탄수화물 g
    @Column(name = "sugar_g")
    private float sugarG; // 당 g
    @Column(name = "protein_g")
    private float proteinG; // 단백질 g
    @Column(name = "fat_g")
    private float fatG; // 지방 g
    @Column(name = "saturated_fat_g")
    private float saturatedFatG; // 포화 지방 g
    @Column(name = "trans_fat_g")
    private float transFatG; // 트랜스 지방 g
    @Column(name = "cholesterol_g")
    private float cholesterolG; // 콜레스테롤 g
    @Column(name = "sodium_g")
    private float sodiumG; // 나트륨 g
    private int likes;
    private double stars;
    private int reviews;
    private int views;

    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;
    private String thumbnail;

    public void minusLikes() {
        this.likes--;
    }

    public void plusLikes() {
        this.likes++;
    }
    public void plusViews() { this.views++; }
}
