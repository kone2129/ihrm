package com.ihrm.system.dao;

import com.ihrm.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

/**
 * User:kone.wang
 * Time:2020/1/20
 **/
@Component
public interface UserDao extends JpaRepository<User, String>,
        JpaSpecificationExecutor<User> {
    User findByMobile(String mobile);
}