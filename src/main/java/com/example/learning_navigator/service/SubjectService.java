package com.example.learning_navigator.service;

import com.example.learning_navigator.exchange.subject.EnrollStudentRequest;
import com.example.learning_navigator.exchange.subject.SubjectRequest;
import com.example.learning_navigator.exchange.subject.SubjectResponse;

import java.util.List;

public interface SubjectService {
  List<SubjectResponse> fetchAllSubjects();
  SubjectResponse fetchSubjectById(Long id);

  SubjectResponse createSubject(SubjectRequest subjectRequest);
  SubjectResponse updateSubjectById(Long id, SubjectRequest subjectRequest);

  void deleteSubjectById(Long id);

  SubjectResponse enrollStudent(Long id, EnrollStudentRequest enrollStudentRequest);
}
