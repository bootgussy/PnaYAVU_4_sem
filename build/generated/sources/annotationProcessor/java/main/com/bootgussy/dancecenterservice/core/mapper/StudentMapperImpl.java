package com.bootgussy.dancecenterservice.core.mapper;

import com.bootgussy.dancecenterservice.api.dto.create.StudentCreateDto;
import com.bootgussy.dancecenterservice.api.dto.response.StudentResponseDto;
import com.bootgussy.dancecenterservice.core.model.Student;
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
public class StudentMapperImpl extends StudentMapper {

    @Override
    public Student toEntity(StudentCreateDto studentCreateDto) {
        if ( studentCreateDto == null ) {
            return null;
        }

        Student.StudentBuilder student = Student.builder();

        student.name( studentCreateDto.getName() );
        student.phoneNumber( studentCreateDto.getPhoneNumber() );

        return student.build();
    }

    @Override
    public StudentResponseDto toResponseDto(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentResponseDto.StudentResponseDtoBuilder studentResponseDto = StudentResponseDto.builder();

        studentResponseDto.id( student.getId() );
        studentResponseDto.name( student.getName() );
        studentResponseDto.phoneNumber( student.getPhoneNumber() );

        studentResponseDto.groupsId( student.getGroups() != null ? student.getGroups().stream().map(g -> g.getId()).toList() : new ArrayList<>() );

        return studentResponseDto.build();
    }

    @Override
    public List<StudentResponseDto> toResponseDtoList(List<Student> students) {
        if ( students == null ) {
            return null;
        }

        List<StudentResponseDto> list = new ArrayList<StudentResponseDto>( students.size() );
        for ( Student student : students ) {
            list.add( toResponseDto( student ) );
        }

        return list;
    }
}
