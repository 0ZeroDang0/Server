package com.example.zerodang.domain.user.entity;

import com.example.zerodang.global.entity.BaseTimeEntity;
import com.example.zerodang.global.security.jwt.ZeroDangRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false)
    private String userName;

    private String userImg;

    private String kakaoId;

    private LocalDateTime lastLogin;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Enumerated(EnumType.STRING)
    private ZeroDangRole zeroDangRole;

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public void setUserStatus(UserStatus userStatus) { this.userStatus = userStatus; }
}
