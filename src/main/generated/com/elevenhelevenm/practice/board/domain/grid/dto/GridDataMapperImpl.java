package com.elevenhelevenm.practice.board.domain.grid.dto;

import com.elevenhelevenm.practice.board.domain.grid.dto.GridDataDto.GridDataDtoBuilder;
import com.elevenhelevenm.practice.board.domain.grid.model.GridData;
import com.elevenhelevenm.practice.board.domain.grid.model.GridData.GridDataBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-16T11:00:09+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11 (Oracle Corporation)"
)
@Component
public class GridDataMapperImpl implements GridDataMapper {

    @Override
    public GridData toEntity(GridDataDto gridDataDto) {
        if ( gridDataDto == null ) {
            return null;
        }

        GridDataBuilder gridData = GridData.builder();

        gridData.workName( gridDataDto.getWorkName() );
        gridData.workStatus( gridDataDto.getWorkStatus() );
        gridData.workType( gridDataDto.getWorkType() );
        gridData.registrationDate( gridDataDto.getRegistrationDate() );
        gridData.completeDate( gridDataDto.getCompleteDate() );
        gridData.isRequiredFile( gridDataDto.getIsRequiredFile() );
        gridData.isResultFile( gridDataDto.getIsResultFile() );
        gridData.isCanceled( gridDataDto.getIsCanceled() );

        return gridData.build();
    }

    @Override
    public GridDataDto toDto(GridData gridData) {
        if ( gridData == null ) {
            return null;
        }

        GridDataDtoBuilder<?, ?> gridDataDto = GridDataDto.builder();

        gridDataDto.id( gridData.getId() );
        gridDataDto.workName( gridData.getWorkName() );
        gridDataDto.workStatus( gridData.getWorkStatus() );
        gridDataDto.workType( gridData.getWorkType() );
        gridDataDto.registrationDate( gridData.getRegistrationDate() );
        gridDataDto.completeDate( gridData.getCompleteDate() );
        gridDataDto.isRequiredFile( gridData.getIsRequiredFile() );
        gridDataDto.isResultFile( gridData.getIsResultFile() );
        gridDataDto.isCanceled( gridData.getIsCanceled() );

        return gridDataDto.build();
    }
}
