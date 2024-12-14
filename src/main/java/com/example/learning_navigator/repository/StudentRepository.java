package com.example.learning_navigator.repository;

import com.example.learning_navigator.data.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
