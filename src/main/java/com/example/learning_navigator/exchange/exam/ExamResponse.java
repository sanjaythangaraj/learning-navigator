package com.example.learning_navigator.exchange.exam;

import com.example.learning_navigator.data.Subject;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class ExamResponse {
  private Long id;
  private Long subjectId;
  private Set<Long> studentIds;
}
