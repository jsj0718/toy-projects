package com.elevenhelevenm.practice.board.domain.grid.dto;

import com.elevenhelevenm.practice.board.domain.grid.model.WorkStatus;
import com.elevenhelevenm.practice.board.domain.grid.model.WorkType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class GridDataDto {

    private Long id;

    private String workName;

    private WorkStatus workStatus;

    private WorkType workType;

    private LocalDateTime registrationDate;

    private LocalDateTime completeDate;

    private Boolean isRequiredFile;

    private Boolean isResultFile;

    private Boolean isCanceled;

/*
    private FileType fileType;
*/

}
