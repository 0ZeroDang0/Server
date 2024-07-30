package com.example.zerodang.domain.article.entity;

import com.example.zerodang.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String summary;

    @Column(nullable = false)
    private LocalDateTime date;
    private String author;
    private String content;
    private String hashTag;
    private int views;
    private int likes;
    private String thumbnail;
}
