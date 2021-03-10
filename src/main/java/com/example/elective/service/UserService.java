package com.example.elective.service;

import com.example.elective.entity.Course;
import com.example.elective.entity.User;
import com.example.elective.entity.UserCourse;
import com.example.elective.entity.base.SystemRuntimeException;
import com.example.elective.entity.dto.UserCourseDTOFactory;
import com.example.elective.entity.rpo.UserRPOFactory;
import com.example.elective.repository.UserCourseRepository;
import com.example.elective.repository.UserRepository;
import com.example.elective.service.base.AbstractCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import static com.example.elective.constant.SystemConstant.USER_KEY;

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
public class UserService extends AbstractCrudService<User, Integer> {

    @Resource
    private UserRepository userRepository;
    @Resource
    private UserCourseRepository userCourseRepository;

    protected UserService(JpaRepository<User, Integer> baseRepository) {
        super(baseRepository);
    }

    /**
     * 登陆
     * @param number
     * @param password
     * @return User
     */
    public User login(String number, String password) {
        User user = userRepository.findByNumber(number);
        if (user == null) {
            throw new SystemRuntimeException("无此用户");
        }
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            throw new SystemRuntimeException("账号或密码错误");
        }
        if (user.getStatus() == 0) {
            throw new SystemRuntimeException("该账号被禁用");
        }
        return user;
    }

    public User getByNumber(String number) {
        return userRepository.findByNumber(number);
    }

    /**
     * 修改密码
     * @param password
     */
    public Boolean modifyPassword(User user, String password) {
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userRepository.save(user);
        return true;
    }

    /**
     * 获取当前登陆的用户
     * @param httpSession
     * @return
     */
    public User getCurrentUser(HttpSession httpSession) {
        Integer userId = (Integer) httpSession.getAttribute(USER_KEY);
        if (userId == null) {
            throw new SystemRuntimeException("请重新登陆");
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new SystemRuntimeException("请重新登陆");
        }
        return user;
    }

    /**
     * 分页查询有效数据
     * @param userPageRPO
     * @return
     */
    public Page<User> findValidByPage(UserRPOFactory.UserPageRPO userPageRPO) {
        return userRepository.findByPage(userPageRPO, userPageRPO.buildPageable());
    }

    /**
     * 查看学生的选修
     * @param student
     * @return
     */
    public List<UserCourse> elective(User student) {
        return userCourseRepository.findByStudent(student);
    }
}
