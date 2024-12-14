package com.example.learning_navigator.exception.exam;

public class StudentAlreadyRegisteredInExamException extends RuntimeException {
  public StudentAlreadyRegisteredInExamException() {
    super("Student already registered in exam!");
  }
  public StudentAlreadyRegisteredInExamException(String message) {
    super(message);
  }
}
