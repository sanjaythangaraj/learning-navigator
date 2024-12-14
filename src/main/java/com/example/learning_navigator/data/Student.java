package com.example.learning_navigator.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"exams", "subjects"})
public class Student {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  @ManyToMany(mappedBy = "students")
  private Set<Subject> subjects;

  @ManyToMany(mappedBy = "students")
  private Set<Exam> exams;

}
