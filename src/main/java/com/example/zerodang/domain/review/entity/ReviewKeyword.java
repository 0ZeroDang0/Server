package com.example.zerodang.domain.review.entity;

import com.example.zerodang.domain.review.entity.Keyword;
import com.example.zerodang.domain.review.entity.Review;
import com.example.zerodang.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewKeyword extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewKeywordId;

    @Enumerated(EnumType.STRING)
    private Keyword keyword;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;
}
