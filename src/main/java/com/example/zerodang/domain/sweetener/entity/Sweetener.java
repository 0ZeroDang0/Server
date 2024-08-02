package com.example.zerodang.domain.sweetener.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sweetener {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sweetenerId;

    private String sweetenerName;

    private String sweetenerDescription;

    @Enumerated(EnumType.STRING)
    private SweetenerStatus sweetenerStatus;
}
