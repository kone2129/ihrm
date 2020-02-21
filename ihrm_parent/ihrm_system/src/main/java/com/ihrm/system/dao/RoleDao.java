package com.ihrm.system.dao;

import com.ihrm.domain.system.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

/**
  * 企业数据访问接口
  */
@Component
public interface RoleDao extends JpaRepository<Role, String>,
        JpaSpecificationExecutor<Role> {
}