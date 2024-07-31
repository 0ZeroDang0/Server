package com.example.zerodang.domain.articleLike.service;

import com.example.zerodang.domain.article.entity.Article;
import com.example.zerodang.domain.article.repository.ArticleRepository;
import com.example.zerodang.domain.article.service.ArticleService;
import com.example.zerodang.domain.articleLike.entity.ArticleLike;
import com.example.zerodang.domain.articleLike.repository.ArticleLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ArticleLikeServiceImpl implements ArticleLikeService {
    private final ArticleLikeRepository articleLikeRepository;
    private final ArticleService articleService;
    @Override
    @Transactional
    public void toggleByArticleId(Long articleId, Long userId) {
        Article findArticle = articleService.getArticle_id(articleId);
        Optional<ArticleLike> optionalArticleLike = articleLikeRepository.findByArticleAndUserId(findArticle, userId);

        if (optionalArticleLike.isPresent()) {
            // 이미 존재하면 좋아요 취소
            articleLikeRepository.deleteById(optionalArticleLike.get().getArticleLikeId());
            findArticle.minusLikes();
        } else {
            // 존재하지 않으면 좋아요 추가
            ArticleLike newLike = ArticleLike.builder()
                    .article(findArticle)
                    .userId(userId)
                    .build();
            articleLikeRepository.save(newLike);
            findArticle.plusLikes();
        }
    }
}
