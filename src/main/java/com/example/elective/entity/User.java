package com.example.elective.entity;

import com.example.elective.entity.base.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/IvenCc
 * @date: 2021/2/27
 * @see: com.example.elective.entity
 * @version: v1.0.0
 */
@Entity
@Table(name = "User")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(name = "name", columnDefinition = "varchar(25) not null")
    private String name;

    @Column(name = "number", columnDefinition = "varchar(11) not null")
    private String number;

    @Column(name = "password", columnDefinition = "varchar(255) not null")
    private String password;

    @Builder.Default
    @Column(name = "role", columnDefinition = "tinyint(2) not null default 1")
    private Short role = 1; // 0 教师 1 学生

    @Builder.Default
    @Column(name = "credit", columnDefinition = "int(11) not null default 0")
    private Integer credit = 0;

    @Builder.Default
    @Column(name = "status", columnDefinition = "tinyint(2) not null default 0")
    private Short status = 1; // 0 不可用 1 可用

}
