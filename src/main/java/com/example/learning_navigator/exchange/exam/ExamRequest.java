package com.example.learning_navigator.exchange.exam;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamRequest {
  @NotNull(message = "Subject Id is mandatory")
  private Long subjectId;
}
