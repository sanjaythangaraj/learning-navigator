package com.example.learning_navigator.controller;

import com.example.learning_navigator.exchange.student.StudentRequest;
import com.example.learning_navigator.exchange.student.StudentResponse;
import com.example.learning_navigator.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

  private final StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping
  public ResponseEntity<List<StudentResponse>> getAllStudents() {
    List<StudentResponse> responses = studentService.fetchAllStudents();
    return ResponseEntity.status(HttpStatus.OK).body(responses);
  }

  @GetMapping("/{id}")
  public ResponseEntity<StudentResponse> getStudent(@PathVariable Long id) {
    StudentResponse response = studentService.fetchStudentById(id);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping()
  public ResponseEntity<StudentResponse> createStudent(@RequestBody @Valid StudentRequest request) {
    StudentResponse response = studentService.createStudent(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<StudentResponse> updateStudent(
      @PathVariable Long id,
      @RequestBody @Valid StudentRequest studentRequest
  )  {
    StudentResponse response = studentService.updateStudentById(id, studentRequest);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<StudentResponse> deleteStudent(@PathVariable Long id) {
    studentService.fetchStudentById(id);
    return ResponseEntity.noContent().build();
  }

}
