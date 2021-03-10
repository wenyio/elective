package com.example.elective.entity.dto;

import com.example.elective.entity.Course;
import com.example.elective.entity.User;
import com.example.elective.entity.UserCourse;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.function.Function;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/IvenCc
 * @date: 2021/3/1
 * @see: com.example.elective.entity.dto
 * @version: v1.0.0
 */
@Component
public class UserCourseDTOFactory {

    @Resource
    private UserDTOFactory userDTOFactory;
    @Resource
    private CourseDTOFactory courseDTOFactory;

    @Data
    @Builder
    public static class UserCourseDTO {

        private Integer studentId;
        private Integer courseId;

        private CourseDTOFactory.CourseDTO course;
        private UserDTOFactory.UserDTO student;

        @Builder.Default
        private Double grade = 0d; // 成绩

        @Builder.Default
        private Short status = 0; // 0 未结算 1 通过 2 未通过
    }

    public Function<UserCourse, UserCourseDTO> pojoToDTO = userCourse -> {
      return UserCourseDTO.builder()
              .courseId(userCourse.getCourse().getId())
              .studentId(userCourse.getStudent().getId())
              .course(courseDTOFactory.pojoToDTO.apply(userCourse.getCourse()))
              .student(userDTOFactory.pojoToDTO(userCourse.getStudent()))
              .grade((userCourse.getGrade()))
              .status(userCourse.getStatus())
              .build();
    };
}
