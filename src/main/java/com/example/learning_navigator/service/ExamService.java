package com.example.learning_navigator.service;

import com.example.learning_navigator.exchange.exam.ExamRequest;
import com.example.learning_navigator.exchange.exam.ExamResponse;
import com.example.learning_navigator.exchange.exam.RegisterStudentRequest;

import java.util.List;

public interface ExamService {
  List<ExamResponse> fetchAllExams();
  ExamResponse fetchExamById(Long id);
  ExamResponse createExam(ExamRequest examRequest);
  ExamResponse updateExamById(Long id, ExamRequest examRequest);
  void deleteExamById(Long id);

  ExamResponse registerStudent(Long id, RegisterStudentRequest registerStudentRequest);
}
