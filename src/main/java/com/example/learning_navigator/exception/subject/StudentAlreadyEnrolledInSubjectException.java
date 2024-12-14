package com.example.learning_navigator.exception.subject;

public class StudentAlreadyEnrolledInSubjectException extends RuntimeException {
  public StudentAlreadyEnrolledInSubjectException() {
    super("Student already enrolled in the subject!");
  }

  public StudentAlreadyEnrolledInSubjectException(String message) {
    super(message);
  }
}
