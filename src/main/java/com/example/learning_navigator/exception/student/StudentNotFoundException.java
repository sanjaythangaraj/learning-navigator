package com.example.learning_navigator.exception.student;

public class StudentNotFoundException extends RuntimeException{
  public StudentNotFoundException() {
    super("Student with given id not found!");
  }

  public StudentNotFoundException(String message) {
    super(message);
  }
}
