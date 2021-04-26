package com.example.elective.web.api;

import com.example.elective.entity.Course;
import com.example.elective.entity.User;
import com.example.elective.entity.UserCourse;
import com.example.elective.entity.base.ResponseResult;
import com.example.elective.entity.base.SystemRuntimeException;
import com.example.elective.entity.dto.UserCourseDTOFactory;
import com.example.elective.service.CourseService;
import com.example.elective.service.UserCourseService;
import com.example.elective.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

// 选修接口
@RestController
@RequestMapping(value = "/api/user/course")
public class UserCourseController {

    @Resource
    private UserService userService;
    @Resource
    private CourseService courseService;
    @Resource
    private UserCourseService userCourseService;
    @Resource
    private HttpSession httpSession;

    // 保存成绩
    @PostMapping("{id}/grade/{grade}")
    public ResponseResult<Void> saveGrade(@PathVariable Integer id, @PathVariable Double grade) {
        User user = userService.getCurrentUser(httpSession);
        if (user.getRole() != 1) {
            throw new SystemRuntimeException("权限不足");
        }
        UserCourse userCourse = userCourseService.getById(id);
        if (courseService == null) {
            throw new SystemRuntimeException("无此数据");
        }
        userCourse.setGrade(grade);
        userCourseService.update(userCourse);
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功");
    }
}
