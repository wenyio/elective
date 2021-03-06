package com.example.elective.web.api;

import com.example.elective.entity.Course;
import com.example.elective.entity.User;
import com.example.elective.entity.UserCourse;
import com.example.elective.entity.base.ResponseResult;
import com.example.elective.entity.base.SystemRuntimeException;
import com.example.elective.entity.dto.CourseDTOFactory;
import com.example.elective.entity.dto.UserCourseDTOFactory;
import com.example.elective.entity.dto.UserDTOFactory;
import com.example.elective.entity.rpo.LoginRPO;
import com.example.elective.entity.rpo.UserRPOFactory;
import com.example.elective.service.CourseService;
import com.example.elective.service.UserCourseService;
import com.example.elective.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static com.example.elective.constant.SystemConstant.USER_KEY;
import static com.example.elective.constant.SystemConstant.USER_ROLE;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/IvenCc
 * @date: 2021/2/28
 * @see: com.example.elective.web.api
 * @version: v1.0.0
 */
@RestController(value = "user.api")
@RequestMapping(value = "/api/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private UserDTOFactory userDTOFactory;
    @Resource
    private UserRPOFactory userRPOFactory;
    @Resource
    private UserCourseDTOFactory userCourseDTOFactory;
    @Resource
    private UserCourseService userCourseService;
    @Resource
    private CourseService courseService;
    @Resource
    private HttpSession httpSession;

    @PostMapping("/login")
    public ResponseResult<UserDTOFactory.UserDTO> login(@RequestBody @Valid LoginRPO loginRPO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), bindingResult.getFieldError().getDefaultMessage());
        }
        User user = userService.login(loginRPO.getNumber(), loginRPO.getPassword());
        if (user != null) {
            // ????????????????????????ID ??????Session
            httpSession.setAttribute(USER_KEY, user.getId());
            httpSession.setAttribute(USER_ROLE, user.getRole());
            return new ResponseResult<>(HttpStatus.OK.value(), "????????????", userDTOFactory.pojoToDTO(user));
        }
        return new ResponseResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "????????????????????????");
    }

    @PostMapping("/modify/password")
    public ResponseResult<Void> modifyPwd(@NotBlank(message = "??????????????????") String password) {
        Boolean result = userService.modifyPassword(userService.getCurrentUser(httpSession), password);
        if (result) { // ??????????????? ??????????????????
            httpSession.removeAttribute(USER_KEY);
        }
        return new ResponseResult<>(HttpStatus.OK.value(), "????????????");
    }

    @PostMapping("list")
    public ResponseResult<Page<UserDTOFactory.UserDTO>> list(@RequestBody UserRPOFactory.UserPageRPO userPageRPO) {
        Page<User> page = userService.findValidByPage(userPageRPO);
        Page<UserDTOFactory.UserDTO> dtos = page.map(user -> userDTOFactory.pojoToDTO(user));
        return new ResponseResult<>(HttpStatus.OK.value(), "????????????", dtos);
    }

    @DeleteMapping("{id}")
    public ResponseResult<Void> deleteById(@PathVariable Integer id) {
        User currentUser = userService.getCurrentUser(httpSession);
        if (currentUser.getRole() != 1) {
            throw new SystemRuntimeException("?????????");
        }
        User user = userService.getById(id);
        if (user != null) {
            user.setDeleted(true);
        }
        userService.update(user);
        return new ResponseResult<>(HttpStatus.OK.value(), "????????????");
    }

    @GetMapping("{id}/status")
    public ResponseResult<Void> changeStatus(@PathVariable Integer id) {
        User currentUser = userService.getCurrentUser(httpSession);
        if (currentUser.getRole() != 1) {
            throw new SystemRuntimeException("?????????");
        }
        User user = userService.getById(id);
        if (user != null) {
            user.setStatus(user.getStatus() == 1 ? (short)0 : (short)1);
        }
        userService.update(user);
        return new ResponseResult<>(HttpStatus.OK.value(), "????????????");
    }

    @PostMapping("")
    public ResponseResult<Void> save(@RequestBody @Valid UserRPOFactory.UserSaveRPO userSaveRPO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), bindingResult.getFieldError().getDefaultMessage());
        }
        User user = userRPOFactory.rpoToPojo(userSaveRPO);
        userService.update(user);
        return new ResponseResult<>(HttpStatus.OK.value(), "????????????");
    }

    /**
     * ????????????????????????
     * @return
     */
    @GetMapping("/elective")
    public ResponseResult<List<UserCourseDTOFactory.UserCourseDTO>> elective() {
        User user = userService.getCurrentUser(httpSession);
        List<UserCourse> userCourses = userService.elective(user);
        List<UserCourseDTOFactory.UserCourseDTO> userCourseDTOS = userCourses.stream().map(userCourseDTOFactory.pojoToDTO).collect(Collectors.toList());
        return new ResponseResult<>(HttpStatus.OK.value(), "????????????", userCourseDTOS);
    }

    /**
     * ????????????????????????
     * @return
     */
    @GetMapping("/elective/{courseId}")
    public ResponseResult<Void> electiveByCourseId(@PathVariable Integer courseId) {
        User user = userService.getCurrentUser(httpSession);
        Course course = courseService.getById(courseId);
        if (course == null) {
            throw new SystemRuntimeException("????????????");
        }
        UserCourse userCourse = userCourseService.getByStudentAndCourse(user, course);
        if (userCourse == null) {
            userCourse = new UserCourse();
            userCourse.setCourse(course);
            userCourse.setStudent(user);
        } else {
            if (!userCourse.isDeleted()) {
                throw new SystemRuntimeException("????????????");
            }
        }
        if (course.getBeginTime().getTime() < new Date().getTime()) {
            throw new SystemRuntimeException("??????????????????");
        }
        if (course.getSelected() > course.getLimitNum()) {
            throw new SystemRuntimeException("????????????");
        }
        userCourse.setDeleted(false);
        course.setSelected(course.getSelected() + 1);
        courseService.update(course);
        userCourseService.update(userCourse);
        return new ResponseResult<>(HttpStatus.OK.value(), "????????????");
    }

    /**
     * ????????????
     * @return
     */
    @GetMapping("/elective/{courseId}/cancel")
    public ResponseResult<Void> cancelElective(@PathVariable Integer courseId) {
        User user = userService.getCurrentUser(httpSession);
        Course course = courseService.getById(courseId);
        if (course == null) {
            throw new SystemRuntimeException("????????????");
        }
        UserCourse userCourse = userCourseService.getByStudentAndCourse(user, course);
        if (userCourse == null) {
            throw new SystemRuntimeException("???????????????");
        }
        if (course.getBeginTime().getTime() < new Date().getTime()) {
            throw new SystemRuntimeException("???????????????????????????");
        }
        userCourse.setDeleted(true);
        course.setSelected(course.getSelected() - 1);
        courseService.update(course);
        userCourseService.update(userCourse);
        return new ResponseResult<>(HttpStatus.OK.value(), "????????????");
    }
}
