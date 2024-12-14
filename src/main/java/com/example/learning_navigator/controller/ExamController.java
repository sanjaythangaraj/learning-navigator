package com.example.learning_navigator.controller;

import com.example.learning_navigator.exchange.exam.ExamRequest;
import com.example.learning_navigator.exchange.exam.ExamResponse;
import com.example.learning_navigator.exchange.exam.RegisterStudentRequest;
import com.example.learning_navigator.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exams")
public class ExamController {

  private final ExamService examService;

  public ExamController(ExamService examService) {
    this.examService = examService;
  }

  @GetMapping
  public ResponseEntity<List<ExamResponse>> fetchAllExams() {
    List<ExamResponse> response = this.examService.fetchAllExams();
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ExamResponse> fetchExamById(@PathVariable Long id) {
    ExamResponse response = this.examService.fetchExamById(id);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping
  public ResponseEntity<ExamResponse> createExam(@RequestBody @Valid ExamRequest request) {
    ExamResponse response = this.examService.createExam(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ExamResponse> updateExam(
      @PathVariable Long id,
      @RequestBody @Valid ExamRequest request) {
    ExamResponse response = this.examService.updateExamById(id, request);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ExamResponse> deleteExam(@PathVariable Long id) {
    examService.deleteExamById(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}")
  public ResponseEntity<ExamResponse> registerExam(
      @PathVariable Long id,
      @RequestBody @Valid RegisterStudentRequest request
  ) {
    ExamResponse response = this.examService.registerStudent(id, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
