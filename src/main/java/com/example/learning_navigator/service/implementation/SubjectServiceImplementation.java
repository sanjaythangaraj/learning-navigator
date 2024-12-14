package com.example.learning_navigator.service.implementation;

import com.example.learning_navigator.data.Student;
import com.example.learning_navigator.data.Subject;
import com.example.learning_navigator.exception.student.StudentNotFoundException;
import com.example.learning_navigator.exception.subject.StudentAlreadyEnrolledInSubjectException;
import com.example.learning_navigator.exception.subject.SubjectNotFoundException;
import com.example.learning_navigator.exchange.subject.EnrollStudentRequest;
import com.example.learning_navigator.exchange.subject.SubjectRequest;
import com.example.learning_navigator.exchange.subject.SubjectResponse;
import com.example.learning_navigator.repository.StudentRepository;
import com.example.learning_navigator.repository.SubjectRepository;
import com.example.learning_navigator.service.SubjectService;
import com.example.learning_navigator.util.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubjectServiceImplementation implements SubjectService {

  private final SubjectRepository subjectRepository;
  private final StudentRepository studentRepository;

  public SubjectServiceImplementation(SubjectRepository subjectRepository, StudentRepository studentRepository) {
    this.subjectRepository = subjectRepository;
    this.studentRepository = studentRepository;
  }

  @Override
  @Transactional()
  public List<SubjectResponse> fetchAllSubjects() {
    return subjectRepository.findAll()
        .stream()
        .map(Mapper.subjectToSubjectResponseMapper)
        .toList();
  }

  @Override
  public SubjectResponse fetchSubjectById(Long id) {
    return subjectRepository.findById(id)
        .map(Mapper.subjectToSubjectResponseMapper)
        .orElseThrow(SubjectNotFoundException::new);
  }

  @Override
  public SubjectResponse createSubject(SubjectRequest subjectRequest) {
    Subject subject = Mapper.subjectRequestToSubjectMapper.apply(subjectRequest);
    Subject savedSubject = subjectRepository.save(subject);
    return Mapper.subjectToSubjectResponseMapper.apply(savedSubject);
  }

  @Override
  public SubjectResponse updateSubjectById(Long id, SubjectRequest subjectRequest) {
    Subject subject = subjectRepository.findById(id).orElseThrow(SubjectNotFoundException::new);
    subject.setName(subjectRequest.getName());
    Subject updatedSubject = subjectRepository.save(subject);
    return Mapper.subjectToSubjectResponseMapper.apply(updatedSubject);
  }

  @Override
  public void deleteSubjectById(Long id) {
    fetchSubjectById(id);
    subjectRepository.deleteById(id);
  }

  @Override
  @Transactional
  public SubjectResponse enrollStudent(Long id, EnrollStudentRequest enrollStudentRequest) {
    Subject subject = subjectRepository.findById(id).orElseThrow(SubjectNotFoundException::new);
    Student student = studentRepository.findById(enrollStudentRequest.getStudentId()).orElseThrow(StudentNotFoundException::new);
    if (!subject.getStudents().stream().map(Student::getId).filter(sId -> sId.equals(id)).toList().isEmpty())
      throw new StudentAlreadyEnrolledInSubjectException();


    subject.getStudents().add(student);

    Subject updatedSubject = subjectRepository.save(subject);
    return Mapper.subjectToSubjectResponseMapper.apply(updatedSubject);
  }

}
