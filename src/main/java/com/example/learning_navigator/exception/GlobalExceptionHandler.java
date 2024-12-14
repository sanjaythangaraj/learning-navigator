package com.example.learning_navigator.exception;

import com.example.learning_navigator.exception.exam.ExamNotFoundException;
import com.example.learning_navigator.exception.exam.ExamSubjectRequiredException;
import com.example.learning_navigator.exception.exam.StudentAlreadyRegisteredInExamException;
import com.example.learning_navigator.exception.student.StudentNotFoundException;
import com.example.learning_navigator.exception.subject.StudentAlreadyEnrolledInSubjectException;
import com.example.learning_navigator.exception.subject.SubjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage handleException(Exception ex, WebRequest request) {
    return new ErrorMessage(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false)
    );
  }

  @ExceptionHandler(StudentAlreadyEnrolledInSubjectException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorMessage handleStudentAlreadyEnrolledInSubjectException(
      StudentAlreadyEnrolledInSubjectException ex,
      WebRequest request) {
    return new ErrorMessage(
        HttpStatus.CONFLICT.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false)
    );
  }

  @ExceptionHandler(StudentAlreadyRegisteredInExamException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorMessage handleStudentAlreadyRegisteredInExamException(
      StudentAlreadyRegisteredInExamException ex,
      WebRequest request) {
    return new ErrorMessage(
        HttpStatus.CONFLICT.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false)
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
    Map<String, String> map = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      map.put(fieldName, errorMessage);
    });

    return new ErrorMessage(
        HttpStatus.BAD_REQUEST.value(),
        new Date(),
        "Validation error(s)",
        request.getDescription(false),
        map
    );
  }

  @ExceptionHandler(StudentNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorMessage handleStudentNotFoundException(StudentNotFoundException ex, WebRequest request) {
    return new ErrorMessage(
        HttpStatus.NOT_FOUND.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false)
    );
  }

  @ExceptionHandler(SubjectNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorMessage handleSubjectNotFoundException(SubjectNotFoundException ex, WebRequest request) {
    return new ErrorMessage(
        HttpStatus.NOT_FOUND.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false)
    );
  }

  @ExceptionHandler(ExamNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorMessage handleExamNotFoundException(ExamNotFoundException ex, WebRequest request) {
    return new ErrorMessage(
        HttpStatus.NOT_FOUND.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false)
    );
  }

  @ExceptionHandler(ExamSubjectRequiredException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorMessage handleExamSubjectRequiredException(ExamSubjectRequiredException ex, WebRequest request) {
    return new ErrorMessage(
        HttpStatus.CONFLICT.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false)
    );
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorMessage handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {

    return new ErrorMessage(
        HttpStatus.BAD_REQUEST.value(),
        new Date(),
        "Invalid type for path variable. Expected type: " + ex.getRequiredType().getSimpleName(),
        request.getDescription(false)
    );
  }
}
