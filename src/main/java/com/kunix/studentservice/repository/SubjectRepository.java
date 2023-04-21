package com.kunix.studentservice.repository;

import com.kunix.studentservice.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
