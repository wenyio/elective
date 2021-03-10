package com.example.elective.repository;

import com.example.elective.entity.Course;
import com.example.elective.entity.User;
import com.example.elective.entity.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/IvenCc
 * @date: 2021/2/28
 * @see: com.example.elective.repository
 * @version: v1.0.0
 */
@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, Integer> {

    @Query("from UserCourse where course = ?1 and deleted = false ")
    List<UserCourse> findByCourse(Course course);

    @Query("from UserCourse where student = ?1 and deleted = false ")
    List<UserCourse> findByStudent(User student);

    UserCourse findByStudentAndCourse(User student, Course course);
}
