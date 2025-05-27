package com.bootgussy.dancecenterservice.core.mapper;

import com.bootgussy.dancecenterservice.api.dto.create.TrainerCreateDto;
import com.bootgussy.dancecenterservice.api.dto.response.TrainerResponseDto;
import com.bootgussy.dancecenterservice.core.model.Trainer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-27T13:13:37+0300",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class TrainerMapperImpl extends TrainerMapper {

    @Override
    public Trainer toEntity(TrainerCreateDto trainerCreateDto) {
        if ( trainerCreateDto == null ) {
            return null;
        }

        Trainer.TrainerBuilder trainer = Trainer.builder();

        trainer.name( trainerCreateDto.getName() );
        trainer.phoneNumber( trainerCreateDto.getPhoneNumber() );
        trainer.danceStyle( trainerCreateDto.getDanceStyle() );

        return trainer.build();
    }

    @Override
    public TrainerResponseDto toResponseDto(Trainer trainer) {
        if ( trainer == null ) {
            return null;
        }

        TrainerResponseDto.TrainerResponseDtoBuilder trainerResponseDto = TrainerResponseDto.builder();

        trainerResponseDto.id( trainer.getId() );
        trainerResponseDto.name( trainer.getName() );
        trainerResponseDto.phoneNumber( trainer.getPhoneNumber() );
        trainerResponseDto.danceStyle( trainer.getDanceStyle() );

        trainerResponseDto.groupsId( trainer.getGroups() != null ? trainer.getGroups().stream().map(g -> g.getId()).toList() : new ArrayList<>() );

        return trainerResponseDto.build();
    }

    @Override
    public List<TrainerResponseDto> toResponseDtoList(List<Trainer> trainers) {
        if ( trainers == null ) {
            return null;
        }

        List<TrainerResponseDto> list = new ArrayList<TrainerResponseDto>( trainers.size() );
        for ( Trainer trainer : trainers ) {
            list.add( toResponseDto( trainer ) );
        }

        return list;
    }
}
