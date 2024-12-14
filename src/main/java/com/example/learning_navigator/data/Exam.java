package com.example.learning_navigator.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"students"})
public class Exam {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "subject_id")
  private Subject subject;

  @ManyToMany
  @JoinTable(
      name = "exam_student",
      joinColumns = @JoinColumn(name = "exam_id"),
      inverseJoinColumns = @JoinColumn(name = "student_id"))
  private Set<Student> students;

}
