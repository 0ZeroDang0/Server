package com.example.zerodang.domain.review.repository;

import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.domain.review.entity.Review;
import com.example.zerodang.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
    Optional<Review> findByUserAndProduct(User user, Product product);
}
