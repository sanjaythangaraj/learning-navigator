package com.example.learning_navigator.exchange.subject;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class EnrollStudentRequest {

  @NotNull(message = "Student Id is mandatory")
  private Long studentId;
}
