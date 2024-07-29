package com.example.zerodang.domain.productLike.service;

public interface ProductLikeService {
    /** 좋아요 토글 **/
    void toggleByProductId(Long productId);
}
