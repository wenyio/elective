package com.example.elective.entity.rpo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/IvenCc
 * @date: 2021/2/28
 * @see: com.example.elective.entity.rpo
 * @version: v1.0.0
 */
@Data
public class LoginRPO implements Serializable {

    @NotBlank(message = "账号不能为空")
    private String number;

    @NotBlank(message = "密码不能为空")
    private String password;
}
