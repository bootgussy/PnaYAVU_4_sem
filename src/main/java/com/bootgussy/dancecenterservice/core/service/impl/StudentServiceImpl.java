package com.bootgussy.dancecenterservice.core.service.impl;

import com.bootgussy.dancecenterservice.core.exception.AlreadyExistsException;
import com.bootgussy.dancecenterservice.core.exception.ResourceNotFoundException;
import com.bootgussy.dancecenterservice.core.model.Student;
import com.bootgussy.dancecenterservice.core.repository.StudentRepository;
import com.bootgussy.dancecenterservice.core.service.StudentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student findStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found. ID: " + id));
    }

    @Override
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student createStudent(Student student) {
        Student savedStudent;

        if (
                student.getName() == null ||
                student.getPhoneNumber() == null
        ) {
            throw new ResourceNotFoundException("Incorrect JSON. All fields must be filled " +
                    "(name, phoneNumber).");
        }

        if (studentRepository.findByNameAndPhoneNumber(student.getName(), student.getPhoneNumber())
                .isEmpty()) {
            savedStudent = studentRepository.save(student);
        } else {
            throw new AlreadyExistsException("Student already exists." +
                    " Name: " + student.getName() +
                    ", Phone number: " + student.getPhoneNumber());
        }

        return savedStudent;
    }

    @Override
    public Student updateStudent(Student student) {
        Student updatedStudent;

        if (
                student.getName() == null ||
                student.getPhoneNumber() == null
        ) {
            throw new ResourceNotFoundException("Incorrect JSON. All fields must be filled " +
                    "(name, phoneNumber).");
        }

        if (!studentRepository.findByNameAndPhoneNumber(student.getName(), student.getPhoneNumber())
                .isEmpty()) {
            throw new AlreadyExistsException("Student already exists." +
                    " Name: " + student.getName() +
                    ", Phone number: " + student.getPhoneNumber());
        }

        if (studentRepository.findById(student.getId()).isPresent()) {
            updatedStudent = studentRepository.save(student);
        } else {
            throw new ResourceNotFoundException("The student does not exist." +
                    " ID: " + student.getId() +
                    ", Name: " + student.getName() +
                    ", Phone number: " + student.getPhoneNumber());
        }

        return updatedStudent;
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found. ID: " + id));

        studentRepository.delete(student);
    }
}
