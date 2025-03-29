
package com.bootgussy.dancecenterservice.api.controller;

import com.bootgussy.dancecenterservice.api.dto.create.StudentCreateDto;
import com.bootgussy.dancecenterservice.api.dto.response.StudentResponseDto;
import com.bootgussy.dancecenterservice.core.mapper.StudentMapper;
import com.bootgussy.dancecenterservice.core.model.Student;
import com.bootgussy.dancecenterservice.core.service.StudentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @GetMapping("/{id}")
    public StudentResponseDto findStudentById(@PathVariable Long id) {
        Student student = studentService.findStudentById(id);
        return studentMapper.toResponseDto(student);
    }

    @GetMapping
    public List<StudentResponseDto> findAllStudents() {
        List<Student> students = studentService.findAllStudents();
        return studentMapper.toResponseDtoList(students);
    }

    @PostMapping
    public StudentResponseDto createStudent(@RequestBody StudentCreateDto createDto) {
        Student student = studentMapper.toEntity(createDto);
        Student createdStudent = studentService.createStudent(student);
        return studentMapper.toResponseDto(createdStudent);
    }

    @PutMapping("/{id}")
    public StudentResponseDto updateStudent(@PathVariable Long id, @RequestBody StudentCreateDto createDto) {
        Student student = studentMapper.toEntity(createDto);
        student.setId(id);
        Student updatedStudent = studentService.updateStudent(student);
        return studentMapper.toResponseDto(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}
