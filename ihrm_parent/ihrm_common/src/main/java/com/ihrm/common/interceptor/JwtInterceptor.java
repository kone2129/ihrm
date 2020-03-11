package com.ihrm.common.interceptor;

import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器，继承HandlerInterceptorAdapter
 */
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtils jwtUtils;

    //进入到控制器之前执行的方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1:通过拦截器获取token的数据
        //1.获取请求头信息：名称=Authorization
        String authorization = request.getHeader("Authorization");
        if(! StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer")) {
            //2.替换Bearer+空格
            String token = authorization.replace("Bearer ","");
            //3.解析token
            Claims claims = jwtUtils.parseJwt(token);
            if (claims != null){
                //获取到用户可访问的api权限字符串
                String apis= (String) claims.get("apis");
                //通过handler
                HandlerMethod h= (HandlerMethod) handler;
                //获取RequestMapping
                RequestMapping r = h.getMethodAnnotation(RequestMapping.class);
                String name = r.name();
                if(apis.contains(name)){
                    request.setAttribute("user_claims",claims);
                    return  true;
                }
            }
        }
        throw new CommonException(ResultCode.UNAUTHENTICATED);

    }
}
