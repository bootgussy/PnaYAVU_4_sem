package com.bootgussy.dancecenterservice.core.mapper;

import com.bootgussy.dancecenterservice.api.dto.create.HallCreateDto;
import com.bootgussy.dancecenterservice.api.dto.response.HallResponseDto;
import com.bootgussy.dancecenterservice.core.model.Hall;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HallMapper {
    HallResponseDto toResponseDto(Hall hall);

    Hall toEntity(HallCreateDto createDto);

    List<HallResponseDto> toResponseDtoList(List<Hall> halls);
}