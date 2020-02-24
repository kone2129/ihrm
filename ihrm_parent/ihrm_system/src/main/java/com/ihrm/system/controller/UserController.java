package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.JwtUtils;
import com.ihrm.domain.system.User;
import com.ihrm.domain.system.response.ProfileResult;
import com.ihrm.domain.system.response.UserResult;
import com.ihrm.system.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User:kone.wang
 * Time:2020/1/20
 **/

@RestController
@RequestMapping("/sys")
@CrossOrigin
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    /**
     *     * 分配角色
     *    
     */
    @RequestMapping(value = "/user/assignRoles", method = RequestMethod.PUT)
    public Result assignRoles(@RequestBody Map<String, Object> map) {
        //1.获取被分配的用户id
        String userId = (String) map.get("id");
        //2.获取到角色的id列表
        List<String> roleIds = (List<String>) map.get("roleIds");
        //3.调用service完成角色分配
        userService.assignRoles(userId, roleIds);
        return new Result(ResultCode.SUCCESS);
    }

    //保存用户
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Result add(@RequestBody User user) throws Exception {
        user.setCompanyId(parseCompanyId());
        user.setCompanyName(parseCompanyName());
        userService.save(user);
        return Result.SUCCESS();
    }

    //更新用户
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(name = "id") String id, @RequestBody User user)
            throws Exception {
        userService.update(user);
        return Result.SUCCESS();
    }

    //删除用户
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(name = "id") String id) throws Exception {
        userService.delete(id);
        return Result.SUCCESS();
    }

    /**
     * 根据ID查询用户
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(name = "id") String id) throws Exception {
        User user = userService.findById(id);
        UserResult userResult = new UserResult(user);
        return new Result(ResultCode.SUCCESS, userResult);
    }

    /**
     * 分页查询用户
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Result findByPage(int page, int size, @RequestParam Map<String, Object>
            map) throws Exception {
        map.put("companyId", parseCompanyId());
        Page<User> searchPage = userService.findSearch(map, page, size);
        PageResult<User> pr = new PageResult(searchPage.getTotalElements(), searchPage.getContent());
        return new Result(ResultCode.SUCCESS, pr);
    }
    /**
     * 用户登录
     *  1.通过service根据mobile查询用户
     *  2.比较password
     *  3.生成jwt信息
     *
     */
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public Result login(@RequestBody Map<String,String> loginMap) {
        String mobile = loginMap.get("mobile");
        String password = loginMap.get("password");
        User user = userService.findByMobile(mobile);
        //登录失败
        if(user == null || !user.getPassword().equals(password)) {
            return new Result(ResultCode.MOBILEORPASSWORDERROR);
        }else {
            //登录成功
            Map<String,Object> map = new HashMap<>();
            map.put("companyId",user.getCompanyId());
            map.put("companyName",user.getCompanyName());
            String token = jwtUtils.createJwt(user.getId(), user.getUsername(), map);
            return new Result(ResultCode.SUCCESS,token);
        }
    }
    /**
     * 用户登录成功之后，获取用户信息
     *      1.获取用户id
     *      2.根据用户id查询用户
     *      3.构建返回值对象
     *      4.响应
     */
    @RequestMapping(value="/profile",method = RequestMethod.POST)
    public Result profile(HttpServletRequest request) throws Exception {

        /**
         * 从请求头信息中获取token数据
         *   1.获取请求头信息：名称=Authorization
         *   2.替换Bearer+空格
         *   3.解析token
         *   4.获取clamis
         */
        //1.获取请求头信息：名称=Authorization
        String authorization = request.getHeader("Authorization");
        if(StringUtils.isEmpty(authorization)) {
            throw new CommonException(ResultCode.UNAUTHENTICATED);
        }
        //2.替换Bearer+空格
        String token = authorization.replace("Bearer ","");
        //3.解析token
        Claims claims = jwtUtils.parseJwt(token);
        String userid = claims.getId();
        User user = userService.findById(userid);
        ProfileResult result = new ProfileResult(user);
        return new Result(ResultCode.SUCCESS,result);
    }
}