package com.example.elective.entity.rpo;

import com.example.elective.entity.Course;
import com.example.elective.entity.User;
import com.example.elective.entity.base.BasePageRPO;
import com.example.elective.entity.base.SystemRuntimeException;
import com.example.elective.service.CourseService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
 * @see: com.example.elective.entity.rpo
 * @version: v1.0.0
 */
@Component
public class CourseRPOFactory {

    @Resource
    private CourseService courseService;

    @Data
    public static class CoursePageRPO extends BasePageRPO {
        private String searchValue;
    }

    @Data
    @Builder
    public static class CourseSaveRPO {

        private Integer id;

        @NotBlank(message = "课程名不能为空")
        private String name;

        @Builder.Default
        private Integer credit = 0;

        @NotNull(message = "开始时间不能为空")
        @JsonFormat(locale="zh", pattern="yyyy-MM-dd HH:mm:ss")
        private Timestamp beginTime;

        @NotNull(message = "结束时间不能为空")
        @JsonFormat(locale="zh", pattern="yyyy-MM-dd HH:mm:ss")
        private Timestamp endTime;

        @Builder.Default
        private Integer limitNum = 0;

        /**
         * 初始化时执行
         * @param beginTime
         */
        @PrePersist
        protected void persist(Timestamp beginTime) {
            if (beginTime.getTime() > endTime.getTime()) {
                throw new SystemRuntimeException("结束时间必须大于开始时间");
            }
        }

        /**
         * 更新时执行
         * @param beginTime
         */
        @PreUpdate
        protected void update(Timestamp beginTime) {
            if (beginTime.getTime() > endTime.getTime()) {
                throw new SystemRuntimeException("结束时间必须大于开始时间");
            }
        }
    }

    public Function<CourseSaveRPO, Course> rpoToPojo = courseSaveRPO -> {
        Course course = null;
        if (courseSaveRPO.getId() != null) {
            course = courseService.getById(courseSaveRPO.getId());
        }
        if (course == null) {
            course = new Course();
        }
        course.setName(courseSaveRPO.getName());
        course.setCredit(courseSaveRPO.getCredit());
        course.setBeginTime(courseSaveRPO.getBeginTime());
        course.setEndTime(courseSaveRPO.getEndTime());
        course.setLimitNum(courseSaveRPO.getLimitNum());
        return course;
    };
}
