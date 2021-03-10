package com.example.elective.entity.dto;

import com.example.elective.entity.Course;
import com.example.elective.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
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
public class CourseDTOFactory {

    @Resource
    private UserDTOFactory userDTOFactory;

    @Data
    @Builder
    public static class CourseDTO {
        private Integer id;

        private String name;

        private UserDTOFactory.UserDTO teacher;

        @Builder.Default
        private Integer credit = 0;

        @JsonFormat(locale="zh", pattern="yyyy-MM-dd HH:mm")
        private Timestamp beginTime;

        @JsonFormat(locale="zh", pattern="yyyy-MM-dd HH:mm")
        private Timestamp endTime;

        @Builder.Default
        private Integer limitNum = 0; // 限制人数

        @Builder.Default
        private Integer selected = 0; // 已选的人数
    }

    public Function<Course, CourseDTO> pojoToDTO = course -> {
        return CourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .credit(course.getCredit())
                .teacher(userDTOFactory.pojoToDTO(course.getTeacher()))
                .beginTime(course.getBeginTime())
                .endTime(course.getEndTime())
                .limitNum(course.getLimitNum())
                .selected(course.getSelected())
                .build();
    };
}
