package com.example.zerodang.domain.article.service;

import com.example.zerodang.domain.article.dto.response.ArticleResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ArticleService {
    /** 아티클 전체 조회 **/
    Page<ArticleResponseDTO.ArticleFindOneDTO> findAll(Pageable pageable);

    /** 아티클 상세 조회 **/
    ArticleResponseDTO.ArticleFindDetailDTO findDetail(Long articleId);
}
