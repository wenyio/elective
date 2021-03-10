package com.example.elective.service;

import com.example.elective.entity.Course;
import com.example.elective.entity.User;
import com.example.elective.entity.UserCourse;
import com.example.elective.repository.UserCourseRepository;
import com.example.elective.service.base.AbstractCrudService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/IvenCc
 * @date: 2021/2/28
 * @see: com.example.elective.service
 * @version: v1.0.0
 */
@Service
public class UserCourseService extends AbstractCrudService<UserCourse, Integer> {

    @Resource
    private UserCourseRepository userCourseRepository;

    protected UserCourseService(JpaRepository<UserCourse, Integer> baseRepository) {
        super(baseRepository);
    }

    public UserCourse getByStudentAndCourse(User student, Course course) {
        return userCourseRepository.findByStudentAndCourse(student, course);
    }

    public List<UserCourse> findByStduent(User student) {
        return userCourseRepository.findByStudent(student);
    }
}
