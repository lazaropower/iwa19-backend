package com.lazaropower.iwa.backend.repository;

import com.lazaropower.iwa.backend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{
    Optional<Course> findByName(String name);
    Boolean existsByName(String name);
}
