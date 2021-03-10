package com.example.elective.entity;

import com.example.elective.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/IvenCc
 * @date: 2021/2/27
 * @see: com.example.elective
 * @version: v1.0.0
 */
@Entity
@Table(name = "Course")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Course extends BaseEntity{

    @Column(name = "name", columnDefinition = "varchar(25) not null")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @Builder.Default
    @Column(name = "credit", columnDefinition = "int(11) not null default 0")
    private Integer credit = 0;

    @JsonFormat(locale="zh", pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "begin_time")
    private Timestamp beginTime;

    @JsonFormat(locale="zh", pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "end_time")
    private Timestamp endTime;

    @Builder.Default
    @Column(name = "limit_num", columnDefinition = "int(11) not null default 0")
    private Integer limitNum = 0;

    @Builder.Default
    @Column(name = "selected", columnDefinition = "int(11) not null default 0")
    private Integer selected = 0; // 已选的人数

}
