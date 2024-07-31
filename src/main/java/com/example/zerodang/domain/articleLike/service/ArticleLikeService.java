package com.example.zerodang.domain.articleLike.service;

public interface ArticleLikeService {
    /** 좋아요 토글 **/
    void toggleByArticleId(Long articleId, Long userId);
}
