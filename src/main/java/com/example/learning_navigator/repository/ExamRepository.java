package com.example.learning_navigator.repository;

import com.example.learning_navigator.data.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}
