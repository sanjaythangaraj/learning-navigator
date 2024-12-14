package com.example.learning_navigator.exception.exam;

public class ExamNotFoundException extends RuntimeException {

  public ExamNotFoundException() {
    super("Exam with given id not found!");
  }

  public ExamNotFoundException(String message) {
    super(message);
  }
}
