package com.lazaropower.iwa.backend.repository;

import com.lazaropower.iwa.backend.model.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, UserCourse.UserCourseId> {
}
