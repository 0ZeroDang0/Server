package com.example.zerodang.domain.comparison.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comparison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comparisonId;
    private Long product1;
    private Long product2;
    private int views;

    public void plusViews() {
        this.views++;
    }
}
