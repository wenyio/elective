package com.example.elective.entity.rpo;
import java.sql.Timestamp;

import com.example.elective.entity.User;
import com.example.elective.entity.base.BasePageRPO;
import com.example.elective.entity.base.SystemRuntimeException;
import com.example.elective.service.UserService;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

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
public class UserRPOFactory {

    @Resource
    private UserService userService;

    @Data
    @Builder
    public static class UserPageRPO extends BasePageRPO {

        private String searchValue; // 搜索字段，为空就查全部

        @Builder.Default
        private Short status = -1; // 0 不可用 1 可用 -1查全部

        @Builder.Default
        private Short role = -1; // 0 教师 1 学生 -1查全部
    }

    @Data
    @Builder
    public static class UserSaveRPO {

        private Integer id;

        @NotBlank(message = "姓名不能为空")
        private String name;

        @NotBlank(message = "学号/工号不能为空")
        private String number;

        @Builder.Default
        private Short role = 1; // 0 教师 1 学生
    }

    public User rpoToPojo(UserSaveRPO rpo) {
        User byNumber = userService.getByNumber(rpo.getNumber());
        User user = null;
        if (rpo.getId() != null) {
            user = userService.getById(rpo.getId());
        }
        if (user == null) {
            if (byNumber != null) {
                throw new SystemRuntimeException("工号/学号重复");
            }
            user = new User();
            user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        } else {
            if (byNumber != null && !rpo.getNumber().equals(user.getNumber())) {
                throw new SystemRuntimeException("工号/学号重复");
            }
        }
        user.setName(rpo.getName());
        user.setNumber(rpo.getNumber());
        user.setRole(rpo.getRole());
        return user;
    }
}
