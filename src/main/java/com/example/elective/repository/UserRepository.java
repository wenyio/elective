package com.example.elective.repository;

import com.blinkfox.fenix.jpa.QueryFenix;
import com.example.elective.entity.User;
import com.example.elective.entity.rpo.UserRPOFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("from User where number = ?1 and deleted = false")
    User findByNumber(String number);

    @QueryFenix
    Page<User> findByPage(UserRPOFactory.UserPageRPO param, Pageable pageable);
}
