package com.example.learning_navigator.exception.subject;

public class SubjectNotFoundException extends RuntimeException {
  public SubjectNotFoundException() {
    super("Subject with given id not found!");
  }

  public SubjectNotFoundException(String message) {
    super(message);
  }
}
