package com.example.learning_navigator.service;

import com.example.learning_navigator.data.Student;
import com.example.learning_navigator.data.Subject;
import com.example.learning_navigator.exception.student.StudentNotFoundException;
import com.example.learning_navigator.exception.subject.StudentAlreadyEnrolledInSubjectException;
import com.example.learning_navigator.exception.subject.SubjectNotFoundException;
import com.example.learning_navigator.exchange.subject.EnrollStudentRequest;
import com.example.learning_navigator.exchange.subject.SubjectResponse;
import com.example.learning_navigator.repository.StudentRepository;
import com.example.learning_navigator.repository.SubjectRepository;
import com.example.learning_navigator.service.implementation.SubjectServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTest {

  @Mock
  private SubjectRepository subjectRepository;

  @Mock
  private StudentRepository studentRepository;

  @InjectMocks
  private SubjectServiceImplementation subjectService;

  @BeforeEach
  public void setup() {
    subjectService = new SubjectServiceImplementation(subjectRepository, studentRepository);
  }

  @Test
  void testEnrollStudentSuccess() {
    Long subjectId = 1L;
    Long studentId = 1L;

    EnrollStudentRequest request = new EnrollStudentRequest(studentId);

    Subject subject = new Subject();
    subject.setId(subjectId);
    subject.setStudents(new HashSet<>());

    Student student = new Student();
    student.setId(studentId);

    when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));
    when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
    when(subjectRepository.save(subject)).thenReturn(subject);

    SubjectResponse response = subjectService.enrollStudent(subjectId, request);

    assertNotNull(response);
    assertEquals(subjectId, response.getId());
    assertTrue(response.getStudentIds().contains(studentId));
  }

  @Test
  public void testEnrollStudentForSubjectNotFound() {
    Long subjectId = 1L;
    Long studentId = 1L;

    EnrollStudentRequest request = new EnrollStudentRequest(studentId);

    when(subjectRepository.findById(subjectId)).thenReturn(Optional.empty());
    assertThrows(SubjectNotFoundException.class, () -> {
      subjectService.enrollStudent(subjectId, request);
    });
  }

  @Test
  public void testEnrollStudentForStudentNotFound() {
    Long subjectId = 1L;
    Long studentId = 1L;

    EnrollStudentRequest request = new EnrollStudentRequest(studentId);

    Subject subject = new Subject();
    subject.setId(subjectId);

    when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));
    when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

    assertThrows(StudentNotFoundException.class, () -> {
      subjectService.enrollStudent(subjectId, request);
    });
  }

  @Test
  public void testEnrollStudentForStudentAlreadyEnrolled() {
    Long subjectId = 1L;
    Long studentId = 1L;

    EnrollStudentRequest request = new EnrollStudentRequest(studentId);

    Subject subject = new Subject();
    subject.setId(subjectId);

    Student student = new Student();
    student.setId(studentId);

    subject.setStudents(Set.of(student));

    when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));
    when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

    assertThrows(StudentAlreadyEnrolledInSubjectException.class, () -> {
      subjectService.enrollStudent(subjectId, request);
    });
  }
}