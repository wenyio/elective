package com.example.elective.service;

import com.example.elective.entity.Course;
import com.example.elective.entity.User;
import com.example.elective.entity.UserCourse;
import com.example.elective.entity.rpo.CourseRPOFactory;
import com.example.elective.repository.CourseRepository;
import com.example.elective.repository.UserCourseRepository;
import com.example.elective.repository.UserRepository;
import com.example.elective.service.base.AbstractCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
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
public class CourseService extends AbstractCrudService<Course, Integer> {

    @Resource
    private UserRepository userRepository;
    @Resource
    private CourseRepository courseRepository;
    @Resource
    private UserCourseRepository userCourseRepository;

    protected CourseService(JpaRepository<Course, Integer> baseRepository) {
        super(baseRepository);
    }

    public Page<Course> findValidByPage(CourseRPOFactory.CoursePageRPO coursePageRPO) {
        return courseRepository.findByPage(coursePageRPO, coursePageRPO.buildPageable());
    }

    public Course getByTeacherAndId(User user, Integer courseId) {
        return courseRepository.findByTeacherAndId(user, courseId);
    }

    /**
     * 结算课程
     * @param course
     */
    @Transactional
    public void settlement(Course course) {
        List<User> users = new ArrayList<>();
        List<UserCourse> userCourses = userCourseRepository.findByCourse(course);
        for (UserCourse userCours : userCourses) {
            if (userCours.getGrade() != null && userCours.getGrade() >= 60) {
                userCours.setStatus((short)1); // 通过
                userCours.getStudent().setCredit(userCours.getStudent().getCredit() + userCours.getCourse().getCredit());
                users.add(userCours.getStudent());
            } else {
                userCours.setStatus((short)2); // 未通过
            }
        }
        course.setDeleted(true);
        userCourseRepository.saveAll(userCourses); // 更新选修状态
        userRepository.saveAll(users); // 更新学分
        courseRepository.save(course);
    }

    /**
     * 查看课程选修状态
     * @param course
     * @return
     */
    public List<UserCourse> elective(Course course) {
        return userCourseRepository.findByCourse(course);
    }

}
