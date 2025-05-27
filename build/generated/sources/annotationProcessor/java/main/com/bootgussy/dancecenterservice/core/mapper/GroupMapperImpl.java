package com.bootgussy.dancecenterservice.core.mapper;

import com.bootgussy.dancecenterservice.api.dto.create.GroupCreateDto;
import com.bootgussy.dancecenterservice.api.dto.response.GroupResponseDto;
import com.bootgussy.dancecenterservice.core.model.Group;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-27T13:13:36+0300",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class GroupMapperImpl extends GroupMapper {

    @Override
    public Group toEntity(GroupCreateDto groupCreateDto) {
        if ( groupCreateDto == null ) {
            return null;
        }

        Group.GroupBuilder group = Group.builder();

        group.difficulty( groupCreateDto.getDifficulty() );

        group.trainer( mapTrainerIdToTrainer(groupCreateDto.getTrainerId()) );
        group.students( mapStudentsIdToStudents(groupCreateDto.getStudentsId()) );

        return group.build();
    }

    @Override
    public GroupResponseDto toResponseDto(Group group) {
        if ( group == null ) {
            return null;
        }

        GroupResponseDto.GroupResponseDtoBuilder groupResponseDto = GroupResponseDto.builder();

        groupResponseDto.id( group.getId() );
        groupResponseDto.difficulty( group.getDifficulty() );

        groupResponseDto.scheduleItemsId( group.getScheduleItems() != null ? group.getScheduleItems().stream().map(s -> s.getId()).toList() : new ArrayList<>() );
        groupResponseDto.students( studentMapper.toResponseDtoList(group.getStudents()) );
        groupResponseDto.trainer( trainerMapper.toResponseDto(group.getTrainer()) );

        return groupResponseDto.build();
    }

    @Override
    public List<GroupResponseDto> toResponseDtoList(List<Group> entities) {
        if ( entities == null ) {
            return null;
        }

        List<GroupResponseDto> list = new ArrayList<GroupResponseDto>( entities.size() );
        for ( Group group : entities ) {
            list.add( toResponseDto( group ) );
        }

        return list;
    }
}
