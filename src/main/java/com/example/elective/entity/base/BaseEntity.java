package com.example.elective.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.sql.Timestamp;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 更新时间
     */
    @JsonFormat(locale="zh", pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated")
    private Timestamp updated;

    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "created")
    private Timestamp created;

    /**
     * 删除标志
     */
    @Column(name = "is_deleted")
    private boolean deleted;

    /**
     * 初始化执行
     */
    @PrePersist
    protected void prePersist() {
        this.deleted = false; // 0 false 1 true 被删除了
        if (created == null) {
            created = new Timestamp(new Date().getTime());
        }

        if (updated == null) {
            updated = new Timestamp(new Date().getTime());
        }
    }

    /**
     * 更新时执行
     */
    @PreUpdate
    protected void preUpdate() {
        updated = new Timestamp(new Date().getTime());
    }
}
