package com.bootgussy.dancecenterservice.core.mapper;

import com.bootgussy.dancecenterservice.api.dto.create.ScheduleItemCreateDto;
import com.bootgussy.dancecenterservice.api.dto.response.ScheduleItemResponseDto;
import com.bootgussy.dancecenterservice.core.model.ScheduleItem;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-27T19:11:45+0300",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class ScheduleItemMapperImpl extends ScheduleItemMapper {

    @Override
    public ScheduleItem toEntity(ScheduleItemCreateDto scheduleItemCreateDto) {
        if ( scheduleItemCreateDto == null ) {
            return null;
        }

        ScheduleItem.ScheduleItemBuilder scheduleItem = ScheduleItem.builder();

        scheduleItem.dayOfWeek( scheduleItemCreateDto.getDayOfWeek() );
        scheduleItem.startTime( scheduleItemCreateDto.getStartTime() );
        scheduleItem.endTime( scheduleItemCreateDto.getEndTime() );

        scheduleItem.hall( mapHallIdToHall(scheduleItemCreateDto.getHallId()) );
        scheduleItem.group( mapGroupIdToGroup(scheduleItemCreateDto.getGroupId()) );

        return scheduleItem.build();
    }

    @Override
    public ScheduleItemResponseDto toResponseDto(ScheduleItem scheduleItem) {
        if ( scheduleItem == null ) {
            return null;
        }

        ScheduleItemResponseDto.ScheduleItemResponseDtoBuilder scheduleItemResponseDto = ScheduleItemResponseDto.builder();

        scheduleItemResponseDto.id( scheduleItem.getId() );
        scheduleItemResponseDto.dayOfWeek( scheduleItem.getDayOfWeek() );
        scheduleItemResponseDto.startTime( scheduleItem.getStartTime() );
        scheduleItemResponseDto.endTime( scheduleItem.getEndTime() );

        scheduleItemResponseDto.hall( hallMapper.toResponseDto(scheduleItem.getHall()) );
        scheduleItemResponseDto.group( groupMapper.toResponseDto(scheduleItem.getGroup()) );

        return scheduleItemResponseDto.build();
    }

    @Override
    public List<ScheduleItemResponseDto> toResponseDtoList(List<ScheduleItem> scheduleItems) {
        if ( scheduleItems == null ) {
            return null;
        }

        List<ScheduleItemResponseDto> list = new ArrayList<ScheduleItemResponseDto>( scheduleItems.size() );
        for ( ScheduleItem scheduleItem : scheduleItems ) {
            list.add( toResponseDto( scheduleItem ) );
        }

        return list;
    }
}
