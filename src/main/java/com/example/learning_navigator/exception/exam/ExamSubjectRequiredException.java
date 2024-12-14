package com.example.learning_navigator.exception.exam;

public class ExamSubjectRequiredException extends RuntimeException {
  public ExamSubjectRequiredException() {
    super("Cannot register for an exam without enrolling in the subject of the exam!");
  }

  public ExamSubjectRequiredException(String message) {
    super(message);
  }
}
