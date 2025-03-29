package com.bootgussy.dancecenterservice.core.mapper;

import com.bootgussy.dancecenterservice.api.dto.create.StudentCreateDto;
import com.bootgussy.dancecenterservice.api.dto.response.StudentResponseDto;
import com.bootgussy.dancecenterservice.core.model.Group;
import com.bootgussy.dancecenterservice.core.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(source = "groups", target = "groupsId", qualifiedByName = "mapGroups")
    StudentResponseDto toResponseDto(Student student);

    List<StudentResponseDto> toResponseDtoList(List<Student> students);

    Student toEntity(StudentCreateDto dto);

    @Named("mapGroups")
    default List<Long> mapGroups(List<Group> groups) {
        if (groups == null) {
            return new ArrayList<>();
        }
        return groups.stream().map(Group::getId).collect(Collectors.toList());
    }
}