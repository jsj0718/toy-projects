package com.elevenhelevenm.practice.board.domain.common.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class BaseTimeDto {
    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;
}
