package com.example.learning_navigator.exchange.subject;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class SubjectRequest {
  @NotBlank(message = "Name is mandatory")
  private String name;
}
