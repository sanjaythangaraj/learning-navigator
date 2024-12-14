package com.example.learning_navigator.util;

import com.example.learning_navigator.data.Exam;
import com.example.learning_navigator.data.Student;
import com.example.learning_navigator.data.Subject;
import com.example.learning_navigator.exchange.exam.ExamResponse;
import com.example.learning_navigator.exchange.student.StudentRequest;
import com.example.learning_navigator.exchange.student.StudentResponse;
import com.example.learning_navigator.exchange.subject.SubjectRequest;
import com.example.learning_navigator.exchange.subject.SubjectResponse;

import java.util.HashSet;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Mapper {

  public static Function<Student, StudentResponse> studentToStudentResponseMapper = student -> StudentResponse.builder()
      .id(student.getId())
      .name(student.getName())
      .subjectIds(student.getSubjects().stream()
          .map(Subject::getId)
          .collect(Collectors.toSet()))
      .examIds(student.getExams().stream()
          .map(Exam::getId)
          .collect(Collectors.toSet()))
      .build();

  public static Function<Subject, SubjectResponse> subjectToSubjectResponseMapper = subject -> SubjectResponse.builder()
      .id(subject.getId())
      .name(subject.getName())
      .studentIds(subject.getStudents().stream()
          .map(Student::getId)
          .collect(Collectors.toSet()))
      .build();

  public static Function<Exam, ExamResponse> examToExamResponseMapper = exam -> ExamResponse.builder()
      .id(exam.getId())
      .subjectId(exam.getSubject().getId())
      .studentIds(exam.getStudents().stream()
          .map(Student::getId)
          .collect(Collectors.toSet()))
      .build();

  public static Function<StudentRequest, Student> studentRequestToStudentMapper = studentRequest -> Student.builder()
      .name(studentRequest.getName())
      .subjects(new HashSet<>())
      .exams(new HashSet<>())
      .build();

  public static Function<SubjectRequest, Subject> subjectRequestToSubjectMapper = subjectRequest -> Subject.builder()
      .name(subjectRequest.getName())
      .students(new HashSet<>())
      .build();
}
