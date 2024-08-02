package com.example.zerodang.domain.article.service;

import com.example.zerodang.domain.article.dto.response.ArticleResponseDTO;
import com.example.zerodang.domain.article.entity.Article;
import com.example.zerodang.domain.article.repository.ArticleRepository;
import com.example.zerodang.global.exception.ErrorCode;
import com.example.zerodang.global.exception.article.ArticleNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    @Override
    public Page<ArticleResponseDTO.ArticleFindOneDTO> findAll(Pageable pageable) {
        return articleRepository.findAllWithPageable(pageable);
    }

    @Override
    @Transactional
    public ArticleResponseDTO.ArticleFindDetailDTO findDetailByArticleId(Long articleId) {
        getArticle_id(articleId).plusViews();
        return articleRepository.findOneDetailByArticleId(articleId);
    }

    @Override
    public Article getArticle_id(Long articleId) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if(!optionalArticle.isPresent()) {
            throw new ArticleNotFoundException(ErrorCode.NOT_FOUND_ARTICLE);
        }
        return optionalArticle.get();
    }

    @Scheduled(cron = "0 0 0 * * *") // 매일 00시에 실행
    @Transactional
    public void resetArticleViews() {
        articleRepository.resetAllArticleViews();
    }
}
