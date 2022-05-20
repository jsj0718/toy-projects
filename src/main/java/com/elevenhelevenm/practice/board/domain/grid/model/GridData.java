package com.elevenhelevenm.practice.board.domain.grid.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class GridData {

    @Id @GeneratedValue
    private Long id;

    private String workName;

    @Enumerated(EnumType.STRING)
    private WorkStatus workStatus;

    @Enumerated(EnumType.STRING)
    private WorkType workType;

    private LocalDateTime registrationDate;

    private LocalDateTime completeDate;

    private Boolean isRequiredFile;

    private Boolean isResultFile;

    private Boolean isCanceled;

/*
    @Enumerated(EnumType.STRING)
    private FileType fileType;
*/

}
