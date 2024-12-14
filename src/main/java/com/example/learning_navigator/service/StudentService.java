package com.example.learning_navigator.service;

import com.example.learning_navigator.exchange.student.StudentRequest;
import com.example.learning_navigator.exchange.student.StudentResponse;

import java.util.List;

public interface StudentService {
  List<StudentResponse> fetchAllStudents();
  StudentResponse fetchStudentById(Long id);
  StudentResponse createStudent(StudentRequest request);
  StudentResponse updateStudentById(Long id, StudentRequest request);
  void deleteStudentById(Long id);
}
