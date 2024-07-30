package com.example.zerodang.domain.article.repository;

import com.example.zerodang.domain.article.dto.response.ArticleResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepositoryCustom {
    ArticleResponseDTO.ArticleFindDetailDTO findOneDetailByArticleId(Long articleId);
    Page<ArticleResponseDTO.ArticleFindOneDTO> findAllWithPageable(Pageable pageable);
}
