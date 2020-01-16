package com.ihrm.company.dao;

/**
 * User:kone.wang
 * Time:2020/1/15
 **/

import com.ihrm.domain.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

/**
 * 企业数据访问接口
 */
@Component
public interface CompanyDao extends JpaRepository<Company, String>,
        JpaSpecificationExecutor<Company> {
}