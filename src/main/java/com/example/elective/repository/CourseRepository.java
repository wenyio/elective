package com.example.elective.repository;

import com.blinkfox.fenix.jpa.QueryFenix;
import com.example.elective.entity.Course;
import com.example.elective.entity.User;
import com.example.elective.entity.rpo.CourseRPOFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/IvenCc
 * @date: 2021/2/28
 * @see: com.example.elective.repository
 * @version: v1.0.0
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    @QueryFenix
    Page<Course> findByPage(CourseRPOFactory.CoursePageRPO param, Pageable pageable);

    @Query("from Course where teacher = ?1 and id = ?2 and deleted = false")
    Course findByTeacherAndId(User teacher, Integer id);
}
