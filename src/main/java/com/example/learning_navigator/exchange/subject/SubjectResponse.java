package com.example.learning_navigator.exchange.subject;

import com.example.learning_navigator.data.Student;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectResponse {
  private Long id;
  private String name;
  private Set<Long> studentIds;
}
