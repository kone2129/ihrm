package com.ihrm.company.controller;

/**
 * User:kone.wang
 * Time:2020/1/19
 **/

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.CompanyService;
import com.ihrm.company.service.DepartmentService;
import com.ihrm.domain.company.Company;
import com.ihrm.domain.company.Department;
import com.ihrm.domain.company.response.DeptListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
@CrossOrigin
public class DepartmentController extends BaseController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CompanyService companyService;
    /**
     * 添加部门
     */
    @RequestMapping(value = "/department", method = RequestMethod.POST)
    public Result add(@RequestBody Department department) throws Exception {
        //设置企业id
        department.setCompanyId(parseCompanyId());
        departmentService.save(department);
        return Result.SUCCESS();
    }
    /**
     * 修改部门信息
     */
    @RequestMapping(value = "/department/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(name = "id") String id, @RequestBody Department
            department) throws Exception {
        //设置企业id
        department.setCompanyId(parseCompanyId());
        department.setId(id);
        departmentService.update(department);
        return Result.SUCCESS();
    }
    /**
     * 删除部门
     */
    @RequestMapping(value = "/department/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(name = "id") String id) throws Exception {
        departmentService.delete(id);
        return Result.SUCCESS();
    }
    /**
     * 根据id查询
     */
    @RequestMapping(value = "/department/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(name = "id") String id) throws Exception {
        Department department = departmentService.findById(id);
        return new Result(ResultCode.SUCCESS,department);
    }
/**
 * 组织架构列表
 */
@RequestMapping(value = "/department", method = RequestMethod.GET)
public Result findAll() throws Exception {
    Company company = companyService.findById(parseCompanyId());
    List<Department> list = departmentService.findAll(parseCompanyId());
    return new Result(ResultCode.SUCCESS,new DeptListResult(company,list));
}
}