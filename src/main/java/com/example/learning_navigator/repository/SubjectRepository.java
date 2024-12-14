package com.example.learning_navigator.repository;

import com.example.learning_navigator.data.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
