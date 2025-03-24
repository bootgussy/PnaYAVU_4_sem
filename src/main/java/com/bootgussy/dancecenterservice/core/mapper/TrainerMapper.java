package com.bootgussy.dancecenterservice.core.mapper;

import com.bootgussy.dancecenterservice.api.dto.create.TrainerCreateDto;
import com.bootgussy.dancecenterservice.api.dto.response.TrainerResponseDto;
import com.bootgussy.dancecenterservice.core.model.Group;
import com.bootgussy.dancecenterservice.core.model.Trainer;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TrainerMapper {
    @Mapping(source = "groups", target = "groupsId", qualifiedByName = "mapGroups")
    TrainerResponseDto toResponseDto(Trainer trainer);

    List<TrainerResponseDto> toResponseDtoList(List<Trainer> trainers);

    Trainer toEntity(TrainerCreateDto dto);

    @Named("mapGroups")
    default List<Long> mapGroups(List<Group> groups) {
        if (groups == null) {
            return null;
        }
        return groups.stream().map(Group::getId).collect(Collectors.toList());
    }
}