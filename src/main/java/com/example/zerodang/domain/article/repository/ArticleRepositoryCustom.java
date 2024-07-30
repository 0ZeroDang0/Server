package com.example.zerodang.domain.article.repository;

import com.example.zerodang.domain.article.dto.response.ArticleResponseDTO;

public interface ArticleRepositoryCustom {
    ArticleResponseDTO.ArticleFindDetailDTO findOneDetailByArticleId(Long articleId);
}
