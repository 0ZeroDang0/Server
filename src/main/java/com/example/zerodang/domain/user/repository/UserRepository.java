package com.example.zerodang.domain.user.repository;

import com.example.zerodang.domain.user.entity.User;
import com.example.zerodang.domain.user.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserIdAndUserStatus(Long userId, UserStatus userStatus);
}
