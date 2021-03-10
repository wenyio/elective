package com.example.elective.entity;

import com.example.elective.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "UserCourse")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserCourse extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;

    @Builder.Default
    @Column(name = "grade", columnDefinition = "double not null default 0")
    private Double grade = 0d; // 成绩

    @Builder.Default
    @Column(name = "status", columnDefinition = "tinyint(2) not null default 0")
    private Short status = 0; // 0 未结算 1 通过 2 未通过
}
