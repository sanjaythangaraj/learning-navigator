package com.example.learning_navigator.service.implementation;

import com.example.learning_navigator.data.Exam;
import com.example.learning_navigator.data.Student;
import com.example.learning_navigator.data.Subject;
import com.example.learning_navigator.exception.exam.ExamNotFoundException;
import com.example.learning_navigator.exception.exam.ExamSubjectRequiredException;
import com.example.learning_navigator.exception.exam.StudentAlreadyRegisteredInExamException;
import com.example.learning_navigator.exception.student.StudentNotFoundException;
import com.example.learning_navigator.exception.subject.SubjectNotFoundException;
import com.example.learning_navigator.exchange.exam.ExamRequest;
import com.example.learning_navigator.exchange.exam.ExamResponse;
import com.example.learning_navigator.exchange.exam.RegisterStudentRequest;
import com.example.learning_navigator.repository.ExamRepository;
import com.example.learning_navigator.repository.StudentRepository;
import com.example.learning_navigator.repository.SubjectRepository;
import com.example.learning_navigator.service.ExamService;
import com.example.learning_navigator.util.Mapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class ExamServiceImplementation implements ExamService {

  private final ExamRepository examRepository;
  private final SubjectRepository subjectRepository;
  private final StudentRepository studentRepository;

  public ExamServiceImplementation(
      ExamRepository examRepository,
      SubjectRepository subjectRepository,
      StudentRepository studentRepository) {
    this.examRepository = examRepository;
    this.subjectRepository = subjectRepository;
    this.studentRepository = studentRepository;
  }

  @Override
  public List<ExamResponse> fetchAllExams() {
    return examRepository.findAll()
        .stream()
        .map(Mapper.examToExamResponseMapper).toList();
  }

  @Override
  public ExamResponse fetchExamById(Long id) {
    return examRepository.findById(id)
        .map(Mapper.examToExamResponseMapper)
        .orElseThrow(ExamNotFoundException::new);
  }

  @Override
  public ExamResponse createExam(ExamRequest examRequest) {
    Subject subject = subjectRepository.findById(examRequest.getSubjectId()).orElseThrow(SubjectNotFoundException::new);

    Exam exam = new Exam();
    exam.setStudents(new HashSet<>());
    exam.setSubject(subject);
    Exam savedExam = examRepository.save(exam);
    return Mapper.examToExamResponseMapper.apply(savedExam);
  }

  @Override
  public ExamResponse updateExamById(Long id, ExamRequest examRequest) {
    Exam exam = examRepository.findById(id).orElseThrow(ExamNotFoundException::new);

    Subject subject = subjectRepository.findById(examRequest.getSubjectId()).orElseThrow(SubjectNotFoundException::new);
    exam.setSubject(subject);
    Exam updatedExam = examRepository.save(exam);
    return Mapper.examToExamResponseMapper.apply(updatedExam);
  }

  @Override
  public void deleteExamById(Long id) {
    fetchExamById(id);
    examRepository.deleteById(id);
  }

  @Override
  public ExamResponse registerStudent(Long id, RegisterStudentRequest registerStudentRequest) {
    Exam exam = examRepository.findById(id).orElseThrow(ExamNotFoundException::new);
    Student student = studentRepository.findById(registerStudentRequest.getStudentId()).orElseThrow(StudentNotFoundException::new);
    if (!student.getSubjects().contains(exam.getSubject())) {
      throw new ExamSubjectRequiredException();
    }

    if (exam.getStudents().contains(student)) {
      throw new StudentAlreadyRegisteredInExamException();
    }

    exam.getStudents().add(student);
    Exam updatedExam = examRepository.save(exam);
    return Mapper.examToExamResponseMapper.apply(updatedExam);
  }

}
