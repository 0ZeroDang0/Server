package com.example.zerodang.domain.articleLike.repository;

import com.example.zerodang.domain.article.entity.Article;
import com.example.zerodang.domain.articleLike.entity.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
    Optional<ArticleLike> findByArticleAndUserId(Article article,Long userId);
}
