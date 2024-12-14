package com.example.learning_navigator.service;

import com.example.learning_navigator.data.Exam;
import com.example.learning_navigator.data.Student;
import com.example.learning_navigator.data.Subject;
import com.example.learning_navigator.exception.exam.ExamNotFoundException;
import com.example.learning_navigator.exception.exam.ExamSubjectRequiredException;
import com.example.learning_navigator.exception.exam.StudentAlreadyRegisteredInExamException;
import com.example.learning_navigator.exception.subject.SubjectNotFoundException;
import com.example.learning_navigator.exchange.exam.ExamResponse;
import com.example.learning_navigator.exchange.exam.RegisterStudentRequest;
import com.example.learning_navigator.repository.ExamRepository;
import com.example.learning_navigator.repository.StudentRepository;
import com.example.learning_navigator.repository.SubjectRepository;
import com.example.learning_navigator.service.implementation.ExamServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExamServiceTest {

  @Mock
  private ExamRepository examRepository;

  @Mock
  private SubjectRepository subjectRepository;

  @Mock
  private StudentRepository studentRepository;

  @InjectMocks
  private ExamServiceImplementation examService;

  @BeforeEach
  public void setup() {
    examService = new ExamServiceImplementation(examRepository, subjectRepository, studentRepository);
  }

  @Test
  public void testRegisterStudentSuccess() {
    Long examId = 1L;
    Long studentId = 1L;
    Long subjectId = 1L;
    RegisterStudentRequest request = new RegisterStudentRequest(studentId);

    Exam exam = new Exam();
    exam.setId(examId);

    Subject subject = new Subject();
    subject.setId(subjectId);

    exam.setSubject(subject);
    exam.setStudents(new HashSet<>());

    Student student = new Student();
    student.setId(studentId);
    student.setSubjects(new HashSet<>(Set.of(subject)));

    when(examRepository.findById(examId)).thenReturn(Optional.of(exam));
    when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
    when(examRepository.save(exam)).thenReturn(exam);

    ExamResponse response = examService.registerStudent(examId, request);

    assertNotNull(response);
    assertEquals(examId, response.getId());
    assertTrue(response.getStudentIds().contains(studentId));
  }

  @Test
  public void testRegisterStudentForExamNotFound() {
    Long examId = 1L;
    Long studentId = 1L;
    RegisterStudentRequest request = new RegisterStudentRequest(studentId);

    when(examRepository.findById(examId)).thenReturn(Optional.empty());

    assertThrows(ExamNotFoundException.class, () -> {
      examService.registerStudent(examId, request);
    });
  }

  @Test
  public void testRegisterStudentForStudentNotFound() {
    Long examId = 1L;
    Long studentId = 1L;

    RegisterStudentRequest request = new RegisterStudentRequest(studentId);

    Exam exam = new Exam();
    exam.setId(examId);

    when(examRepository.findById(examId)).thenReturn(Optional.of(exam));
    when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

    assertThrows(SubjectNotFoundException.class, () -> {
      examService.registerStudent(examId, request);
    });
  }

  @Test
  public void testRegisterStudentForStudentNotEnrolledInSubject() {
    Long examId = 1L;
    Long studentId = 1L;
    Long subjectId = 1L;

    RegisterStudentRequest request = new RegisterStudentRequest(studentId);

    Exam exam = new Exam();
    exam.setId(examId);

    Subject subject = new Subject();
    subject.setId(subjectId);

    exam.setSubject(subject);

    Student student = new Student();
    student.setId(studentId);
    student.setSubjects(new HashSet<>());

    when(examRepository.findById(examId)).thenReturn(Optional.of(exam));
    when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

    assertThrows(ExamSubjectRequiredException.class, () -> {
      examService.registerStudent(examId, request);
    });
  }

  @Test
  public void testRegisterStudentForStudentAlreadyRegistered() {
    Long examId = 1L;
    Long subjectId = 1L;
    Long studentId = 1L;

    RegisterStudentRequest request = new RegisterStudentRequest(studentId);

    Exam exam = new Exam();
    exam.setId(examId);

    Subject subject = new Subject();
    exam.setSubject(subject);

    Student student = new Student();
    student.setSubjects(new HashSet<>(Set.of(subject)));
    exam.setStudents(new HashSet<>(Set.of(student)));

    when(examRepository.findById(examId)).thenReturn(Optional.of(exam));
    when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

    assertThrows(StudentAlreadyRegisteredInExamException.class, () -> {
      examService.registerStudent(examId, request);
    });

  }



}