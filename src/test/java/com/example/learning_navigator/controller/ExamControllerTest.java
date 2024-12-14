package com.example.learning_navigator.controller;

import com.example.learning_navigator.exception.exam.ExamNotFoundException;
import com.example.learning_navigator.exception.exam.ExamSubjectRequiredException;
import com.example.learning_navigator.exception.exam.StudentAlreadyRegisteredInExamException;
import com.example.learning_navigator.exception.student.StudentNotFoundException;
import com.example.learning_navigator.exchange.exam.ExamResponse;
import com.example.learning_navigator.exchange.exam.RegisterStudentRequest;
import com.example.learning_navigator.service.ExamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExamController.class)
class ExamControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private ExamService examService;

  @Test
  void testRegisterStudentSuccess() throws Exception {
    Long examId = 1L;
    Long subjectId = 1L;
    Long studentId = 1L;

    RegisterStudentRequest request = new RegisterStudentRequest(studentId);
    ExamResponse response = new ExamResponse(examId, subjectId, Set.of(studentId));

    when(examService.registerStudent(examId, request)).thenReturn(response);

    mockMvc.perform(post("/exams/{id}", examId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(examId))
        .andExpect(jsonPath("$.subjectId").value(subjectId))
        .andExpect(jsonPath("$.studentIds[0]").value(studentId));

  }

  @Test
  void testRegisterStudentForExamNotFound() throws Exception {
    Long examId = 1L;
    Long studentId = 1L;

    RegisterStudentRequest request = new RegisterStudentRequest(studentId);

    when(examService.registerStudent(examId, request)).thenThrow(new ExamNotFoundException());

    mockMvc.perform(post("/exams/{id}", examId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.statusCode").value(404))
        .andExpect(jsonPath("$.message").value("Exam with given id not found!"))
        .andExpect(jsonPath("$.description").value("uri=/exams/" + examId));
  }

  @Test
  void testRegisterStudentForStudentNotFound() throws Exception {
    Long examId = 1L;
    Long studentId = 1L;

    RegisterStudentRequest request = new RegisterStudentRequest(studentId);

    when(examService.registerStudent(examId, request)).thenThrow(new StudentNotFoundException());

    mockMvc.perform(post("/exams/{id}", examId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.statusCode").value(404))
        .andExpect(jsonPath("$.message").value("Student with given id not found!"))
        .andExpect(jsonPath("$.description").value("uri=/exams/" + examId));
  }

  @Test
  void testRegisterStudentForStudentAlreadyRegistered() throws Exception {
    Long examId = 1L;
    Long studentId = 1L;

    RegisterStudentRequest request = new RegisterStudentRequest(studentId);

    when(examService.registerStudent(examId, request)).thenThrow(new StudentAlreadyRegisteredInExamException());

    mockMvc.perform(post("/exams/{id}", examId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.statusCode").value(409))
        .andExpect(jsonPath("$.message").value("Student already registered in exam!"))
        .andExpect(jsonPath("$.description").value("uri=/exams/" + examId));
  }

  @Test
  void testRegisterStudentWithoutEnrollingInSubject() throws Exception {
    Long examId = 1L;
    Long studentId = 1L;

    RegisterStudentRequest request = new RegisterStudentRequest(studentId);

    when(examService.registerStudent(examId, request)).thenThrow(new ExamSubjectRequiredException());

    mockMvc.perform(post("/exams/{id}", examId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.statusCode").value(409))
        .andExpect(jsonPath("$.message").value("Cannot register for an exam without enrolling in the subject of the exam!"))
        .andExpect(jsonPath("$.description").value("uri=/exams/" + examId));
  }


}