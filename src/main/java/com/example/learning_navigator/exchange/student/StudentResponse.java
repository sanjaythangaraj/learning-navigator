package com.example.learning_navigator.exchange.student;

import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponse {
  private Long id;
  private String name;
  private Set<Long> subjectIds;
  private Set<Long> examIds;
}
