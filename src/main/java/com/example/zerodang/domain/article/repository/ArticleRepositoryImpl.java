package com.example.zerodang.domain.article.repository;

import com.example.zerodang.domain.article.dto.response.ArticleResponseDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.example.zerodang.domain.article.entity.QArticle.article;
import static com.example.zerodang.domain.articleImage.entity.QArticleImage.articleImage;

public class ArticleRepositoryImpl implements ArticleRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ArticleRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public ArticleResponseDTO.ArticleFindDetailDTO findOneDetailByArticleId(Long articleId) {
        ArticleResponseDTO.ArticleFindDetailDTO result = queryFactory.select(Projections.constructor(ArticleResponseDTO.ArticleFindDetailDTO.class,
                article.articleId,
                article.title,
                article.author,
                article.summary,
                article.content,
                article.date,
                article.hashTag,
                article.views,
                article.likes
                ))
                .from(article)
                .where(article.articleId.eq(articleId))
                .fetchOne();

        List<String> articleUrlList = queryFactory.select(articleImage.url)
                .from(articleImage)
                .where(articleImage.article.articleId.eq(articleId))
                .fetch();

        result.setArticleImageList(articleUrlList);

        return result;
    }
}
