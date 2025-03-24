package com.bootgussy.dancecenterservice.core.mapper;

import com.bootgussy.dancecenterservice.api.dto.create.GroupCreateDto;
import com.bootgussy.dancecenterservice.api.dto.response.GroupResponseDto;
import com.bootgussy.dancecenterservice.core.model.Group;
import com.bootgussy.dancecenterservice.core.model.Student;
import com.bootgussy.dancecenterservice.core.model.Trainer;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    @Mapping(source = "trainer.id", target = "trainerId")
    @Mapping(target = "studentsId", expression = "java(mapStudentIds(group.getStudents()))")
    GroupResponseDto toResponseDto(Group group);

    @Mapping(source = "trainerId", target = "trainer", qualifiedByName = "mapTrainer")
    @Mapping(target = "students", expression = "java(mapStudents(dto.getStudentsId()))")
    Group toEntity(GroupCreateDto dto);

    @Named("mapTrainer")
    default Trainer mapTrainer(Long trainerId) {
        if (trainerId == null) {
            return null;
        }
        Trainer trainer = new Trainer();
        trainer.setId(trainerId);
        return trainer;
    }

    @Named("mapStudents")
    default List<Student> mapStudents(List<Long> studentIds) {
        if (studentIds == null) {
            return null;
        }
        return studentIds.stream().map(id -> {
            Student student = new Student();
            student.setId(id);
            return student;
        }).collect(Collectors.toList());
    }

    @Named("mapStudentIds")
    default List<Long> mapStudentIds(List<Student> students) {
        if (students == null) {
            return null;
        }
        return students.stream()
                .map(Student::getId)
                .collect(Collectors.toList());
    }

    List<GroupResponseDto> toResponseDtoList(List<Group> groups);
}