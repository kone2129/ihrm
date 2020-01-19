package com.ihrm.company.dao;

import com.ihrm.domain.company.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

/**
 * User:kone.wang
 * Time:2020/1/19
 **/
@Component
public interface DepartmentDao extends JpaRepository<Department,String>, JpaSpecificationExecutor<Department> {

}
