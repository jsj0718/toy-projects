package com.elevenhelevenm.practice.board.domain.board.dto;

import com.elevenhelevenm.practice.board.domain.common.dto.BaseTimeDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class BoardDto extends BaseTimeDto implements Serializable {
    private Long id;

    @NotBlank(message = "게시글 제목은 필수사항입니다.")
    private String title;

    @NotBlank(message = "게시글 내용은 필수사항입니다.")
    private String content;

    private String author;
}
