package com.example.zerodang.domain.articleLike.entity;

import com.example.zerodang.domain.article.entity.Article;
import com.example.zerodang.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleLikeId;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;
}
