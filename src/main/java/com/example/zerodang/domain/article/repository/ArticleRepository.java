package com.example.zerodang.domain.article.repository;

import com.example.zerodang.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {
    @Modifying
    @Query("UPDATE Article a SET a.views = 0")
    void resetAllArticleViews();
}
