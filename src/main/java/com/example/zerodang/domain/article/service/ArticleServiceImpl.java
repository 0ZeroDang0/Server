package com.example.zerodang.domain.article.service;

import com.example.zerodang.domain.article.dto.response.ArticleResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    @Override
    public ArticleResponseDTO.ArticleFindAllDTO findAll() {
        return null;
    }

    @Override
    public ArticleResponseDTO.ArticleFindDetailDTO findOneDetailByArticleId(Long articleId) {
        return null;
    }
}
