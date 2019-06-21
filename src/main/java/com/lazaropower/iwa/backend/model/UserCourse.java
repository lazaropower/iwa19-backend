package com.lazaropower.iwa.backend.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="user_course")
public class UserCourse implements Serializable {

    @EmbeddedId
    private UserCourseId id;

    //Our extra column
    private Float grade;

    public UserCourse(){

    }

    public UserCourse(User user, Course course, Float grade){
        UserCourseId userCourseId = new UserCourseId();
        userCourseId.userId = user.getId();
        userCourseId.courseId = course.getId();
        this.grade = grade;
    }

    @Embeddable
    public static class UserCourseId implements Serializable {

        private Long userId;
        private Long courseId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserCourseId that = (UserCourseId) o;
            return Objects.equals(userId, that.userId) &&
                    Objects.equals(courseId, that.courseId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, courseId);
        }
    }
}


