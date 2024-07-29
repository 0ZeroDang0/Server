package com.example.zerodang.domain.user.repository;

import com.example.zerodang.domain.user.entity.User;
import com.example.zerodang.domain.user.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String userEmail);
    boolean existsByUserIdAndUserStatus(Long userId, UserStatus userStatus);
}
