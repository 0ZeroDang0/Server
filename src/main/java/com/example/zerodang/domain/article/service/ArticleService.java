package com.example.zerodang.domain.article.service;

import com.example.zerodang.domain.article.dto.response.ArticleResponseDTO;

public interface ArticleService {
    /** 아티클 전체 조회 **/
    ArticleResponseDTO.ArticleFindAllDTO findAll();

    /** 아티클 상세 조회 **/
    ArticleResponseDTO.ArticleFindDetailDTO findDetail(Long articleId);
}
