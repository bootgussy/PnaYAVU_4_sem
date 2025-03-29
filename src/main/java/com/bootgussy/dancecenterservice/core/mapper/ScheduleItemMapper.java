package com.bootgussy.dancecenterservice.core.mapper;

import com.bootgussy.dancecenterservice.api.dto.create.ScheduleItemCreateDto;
import com.bootgussy.dancecenterservice.api.dto.response.ScheduleItemResponseDto;
import com.bootgussy.dancecenterservice.core.exception.ResourceNotFoundException;
import com.bootgussy.dancecenterservice.core.model.Group;
import com.bootgussy.dancecenterservice.core.model.Hall;
import com.bootgussy.dancecenterservice.core.model.ScheduleItem;
import com.bootgussy.dancecenterservice.core.repository.GroupRepository;
import com.bootgussy.dancecenterservice.core.repository.HallRepository;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class ScheduleItemMapper {
    @Autowired
    HallRepository hallRepository;

    @Autowired
    GroupRepository groupRepository;

    @Mapping(target = "hall", source = "hallId")
    @Mapping(target = "group", source = "groupId")
    public abstract ScheduleItem toEntity(ScheduleItemCreateDto createDto);

    @Mapping(target = "hallId", source = "hall.id")
    @Mapping(target = "groupId", source = "group.id")
    public abstract ScheduleItemResponseDto toResponseDto(ScheduleItem scheduleItem);

    public abstract List<ScheduleItemResponseDto> toResponseDtoList(List<ScheduleItem> scheduleItems);

    protected Hall hallFromId(Long hallId) {
        return hallId == null ? null
                : hallRepository.findById(hallId)
                .orElseThrow(() -> new ResourceNotFoundException("Hall not found with id " + hallId));
    }

    protected Group groupFromId(Long groupId) {
        return groupId == null ? null
                : groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with id " + groupId));
    }
}