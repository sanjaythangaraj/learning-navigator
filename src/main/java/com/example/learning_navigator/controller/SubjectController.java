package com.example.learning_navigator.controller;

import com.example.learning_navigator.exchange.subject.EnrollStudentRequest;
import com.example.learning_navigator.exchange.subject.SubjectRequest;
import com.example.learning_navigator.exchange.subject.SubjectResponse;
import com.example.learning_navigator.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

  private final SubjectService subjectService;

  public SubjectController(SubjectService subjectService) {
    this.subjectService = subjectService;
  }

  @GetMapping
  public ResponseEntity<List<SubjectResponse>> getAllSubjects() {
    List<SubjectResponse> responses = subjectService.fetchAllSubjects();
    return ResponseEntity.status(HttpStatus.OK).body(responses);
  }

  @GetMapping("/{id}")
  public ResponseEntity<SubjectResponse> getSubject(@PathVariable Long id) {
    SubjectResponse response = subjectService.fetchSubjectById(id);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping
  public ResponseEntity<SubjectResponse> createSubject(@RequestBody @Valid SubjectRequest request) {
    SubjectResponse response = subjectService.createSubject(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<SubjectResponse> updateSubject(
      @PathVariable Long id,
      @RequestBody @Valid SubjectRequest request
  ) {
    SubjectResponse response = subjectService.updateSubjectById(id, request);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<SubjectResponse> deleteSubject(@PathVariable Long id) {
    subjectService.deleteSubjectById(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}")
  public ResponseEntity<SubjectResponse> enrollStudent(
      @PathVariable Long id,
      @RequestBody @Valid EnrollStudentRequest request
  ) {
    SubjectResponse response = subjectService.enrollStudent(id, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
