package com.example.learning_navigator.controller;

import com.example.learning_navigator.exception.student.StudentNotFoundException;
import com.example.learning_navigator.exception.subject.StudentAlreadyEnrolledInSubjectException;
import com.example.learning_navigator.exception.subject.SubjectNotFoundException;
import com.example.learning_navigator.exchange.subject.EnrollStudentRequest;
import com.example.learning_navigator.exchange.subject.SubjectResponse;
import com.example.learning_navigator.service.SubjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubjectController.class)
public class SubjectControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private SubjectService subjectService;

  @Test
  void testEnrollStudentInSubjectSuccess() throws Exception {
    Long subjectId = 1L;
    Long studentId = 1L;

    EnrollStudentRequest request = new EnrollStudentRequest(studentId);
    SubjectResponse response = new SubjectResponse(subjectId, "Physics", Set.of(studentId));

    when(subjectService.enrollStudent(subjectId, request)).thenReturn(response);

    mockMvc.perform(post("/subjects/{id}", subjectId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(subjectId))
        .andExpect(jsonPath("$.name").value("Physics"))
        .andExpect(jsonPath("$.studentIds[0]").value(studentId));
  }

  @Test
  void testEnrollStudentInSubjectForSubjectNotFound() throws Exception {
    Long subjectId = 1L;
    Long studentId = 1L;

    EnrollStudentRequest request = new EnrollStudentRequest(studentId);

    when(subjectService.enrollStudent(subjectId, request)).thenThrow(new SubjectNotFoundException());

    mockMvc.perform(post("/subjects/{id}", subjectId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.statusCode").value(404))
        .andExpect(jsonPath("$.message").value("Subject with given id not found!"))
        .andExpect(jsonPath("$.description").value("uri=/subjects/" + subjectId));
  }

  @Test
  void testEnrollStudentInSubjectForStudentNotFound() throws Exception {
    Long subjectId = 1L;
    Long studentId = 1L;

    EnrollStudentRequest request = new EnrollStudentRequest(studentId);

    when(subjectService.enrollStudent(subjectId, request)).thenThrow(new StudentNotFoundException());

    mockMvc.perform(post("/subjects/{id}", subjectId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.statusCode").value(404))
        .andExpect(jsonPath("$.message").value("Student with given id not found!"))
        .andExpect(jsonPath("$.description").value("uri=/subjects/" + subjectId));
  }

  @Test
  void testEnrollStudentInSubjectForStudentAlreadyEnrolled() throws Exception {
    Long subjectId = 1L;
    Long studentId = 1L;

    EnrollStudentRequest request = new EnrollStudentRequest(studentId);

    when(subjectService.enrollStudent(subjectId, request)).thenThrow(new StudentAlreadyEnrolledInSubjectException());

    mockMvc.perform(post("/subjects/{id}", subjectId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.statusCode").value(409))
        .andExpect(jsonPath("$.message").value("Student already enrolled in the subject!"))
        .andExpect(jsonPath("$.description").value("uri=/subjects/" + subjectId));
  }
}
