package com.example.zerodang.domain.review.mapper;

import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.domain.review.dto.request.ReviewRequestDTO;
import com.example.zerodang.domain.review.dto.response.ReviewResponseDTO;
import com.example.zerodang.domain.review.entity.Review;
import com.example.zerodang.domain.reviewKeyword.entity.ReviewKeyword;
import com.example.zerodang.domain.user.dto.request.UserRequestDTO;
import com.example.zerodang.domain.user.entity.User;
import com.example.zerodang.domain.user.entity.UserStatus;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReviewMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public Review toReviewEntity(ReviewRequestDTO.ReviewSaveDTO reviewSaveDTO, Product product, User user) {
        return Review.builder()
                .rating(reviewSaveDTO.getRating())
                .user(user)
                .product(product)
                .build();
    }

    public ReviewResponseDTO.ReviewDetailDTO toReviewDetailDTO(Review review, List<ReviewKeyword> reviewKeywords) {
        List<ReviewResponseDTO.KeyWordDTO> keywordDTOs = reviewKeywords.stream()
                .map(keyword -> new ReviewResponseDTO.KeyWordDTO(keyword.getReviewKeywordId(), keyword.getKeyword().name()))
                .collect(Collectors.toList());

        return ReviewResponseDTO.ReviewDetailDTO.builder()
                .userId(review.getUser().getUserId())
                .userName(review.getUser().getUserName())
                .rating(review.getRating())
                .keyWordDTOList(keywordDTOs)
                .build();
    }
}
