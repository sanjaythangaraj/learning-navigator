package com.example.learning_navigator.service.implementation;

import com.example.learning_navigator.data.Student;
import com.example.learning_navigator.exception.student.StudentNotFoundException;
import com.example.learning_navigator.exchange.student.StudentRequest;
import com.example.learning_navigator.exchange.student.StudentResponse;
import com.example.learning_navigator.repository.StudentRepository;
import com.example.learning_navigator.service.StudentService;
import com.example.learning_navigator.util.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImplementation implements StudentService {

  private final StudentRepository studentRepository;

  public StudentServiceImplementation(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @Override
  public List<StudentResponse> fetchAllStudents() {
    return studentRepository.findAll()
        .stream()
        .map(Mapper.studentToStudentResponseMapper)
        .toList();
  }

  @Override
  public StudentResponse fetchStudentById(Long id) {
    Optional<Student> studentOptional = studentRepository.findById(id);
    return studentOptional.map(Mapper.studentToStudentResponseMapper)
        .orElseThrow(StudentNotFoundException::new);
  }

  @Override
  public StudentResponse createStudent(StudentRequest studentRequest) {
    Student student = Mapper.studentRequestToStudentMapper.apply(studentRequest);
    Student savedStudent = studentRepository.save(student);
    return Mapper.studentToStudentResponseMapper.apply(savedStudent);
  }

  @Override
  public StudentResponse updateStudentById(Long id, StudentRequest studentRequest) {
    Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
    student.setName(studentRequest.getName());
    Student updatedStudent = studentRepository.save(student);
    return Mapper.studentToStudentResponseMapper.apply(updatedStudent);
  }

  @Override
  public void deleteStudentById(Long id) {
    fetchStudentById(id);
    studentRepository.deleteById(id);
  }

}
