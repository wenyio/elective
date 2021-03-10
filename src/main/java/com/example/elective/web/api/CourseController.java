package com.example.elective.web.api;

import com.example.elective.entity.Course;
import com.example.elective.entity.User;
import com.example.elective.entity.UserCourse;
import com.example.elective.entity.base.ResponseResult;
import com.example.elective.entity.base.SystemRuntimeException;
import com.example.elective.entity.dto.CourseDTOFactory;
import com.example.elective.entity.dto.UserCourseDTOFactory;
import com.example.elective.entity.dto.UserDTOFactory;
import com.example.elective.entity.rpo.CourseRPOFactory;
import com.example.elective.entity.rpo.UserRPOFactory;
import com.example.elective.service.CourseService;
import com.example.elective.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/IvenCc
 * @date: 2021/3/1
 * @see: com.example.elective.web.api
 * @version: v1.0.0
 */
@RestController(value = "course.api")
@RequestMapping(value = "/api/course")
public class CourseController {

    @Resource
    private CourseService courseService;
    @Resource
    private CourseDTOFactory courseDTOFactory;
    @Resource
    private CourseRPOFactory courseRPOFactory;
    @Resource
    private UserCourseDTOFactory userCourseDTOFactory;
    @Resource
    private UserService userService;
    @Resource
    private HttpSession httpSession;

    @PostMapping("")
    public ResponseResult<Void> save(@RequestBody @Valid CourseRPOFactory.CourseSaveRPO courseSaveRPO, BindingResult bindingResult) {
        User user = userService.getCurrentUser(httpSession);
        if (bindingResult.hasErrors()) {
            return new ResponseResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), bindingResult.getFieldError().getDefaultMessage());
        }
        Course course = courseRPOFactory.rpoToPojo.apply(courseSaveRPO);
        course.setTeacher(user);
        courseService.update(course);
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功");
    }

    @PostMapping("list")
    public ResponseResult<Page<CourseDTOFactory.CourseDTO>> list(@RequestBody CourseRPOFactory.CoursePageRPO coursePageRPO) {
        Page<Course> page = courseService.findValidByPage(coursePageRPO);
        Page<CourseDTOFactory.CourseDTO> dtos = page.map(courseDTOFactory.pojoToDTO);
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功", dtos);
    }

    @DeleteMapping("{id}")
    public ResponseResult<Void> deleteById(@PathVariable Integer id) {
        User user = userService.getCurrentUser(httpSession);
        Course course = courseService.getByTeacherAndId(user, id);
        if (course == null) {
            throw new SystemRuntimeException("无此课程或权限不足");
        }
        course.setDeleted(true);
        courseService.update(course);
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功");
    }

    /**
     * 结算课程，只有课程endTime小于当前时间才能结算
     * @param id
     * @return
     */
    @GetMapping("{id}/settlement")
    public ResponseResult<Void> settlement(@PathVariable Integer id) {
        User user = userService.getCurrentUser(httpSession);
        Course course = courseService.getByTeacherAndId(user, id);
        if (course == null) {
            throw new SystemRuntimeException("无此课程或权限不足");
        }
        if (course.getEndTime().getTime() > new Date().getTime()) {
            throw new SystemRuntimeException("课程还未结束");
        }
        courseService.settlement(course);
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功");
    }

    /**
     * 查看课程选修情况
     * @param id
     * @return
     */
    @GetMapping("{id}/elective")
    public ResponseResult<List<UserCourseDTOFactory.UserCourseDTO>> elective(@PathVariable Integer id) {
        User user = userService.getCurrentUser(httpSession);
        if (user.getRole() != 1) {
            throw new SystemRuntimeException("权限不足");
        }
        Course course = courseService.getById(id);
        if (course == null) {
            throw new SystemRuntimeException("无此课程或权限不足");
        }
        List<UserCourse> userCourses = courseService.elective(course);
        List<UserCourseDTOFactory.UserCourseDTO> userCourseDTOS = userCourses.stream().map(userCourseDTOFactory.pojoToDTO).collect(Collectors.toList());
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功", userCourseDTOS);
    }
}
