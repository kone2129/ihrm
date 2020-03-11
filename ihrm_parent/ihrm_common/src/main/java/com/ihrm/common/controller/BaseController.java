package com.ihrm.common.controller;

/**
 * User:kone.wang
 * Time:2020/1/19
 **/
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.ModelAttribute;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 公共controller
 * 获取request，response
 * 获取企业id，获取企业名称
 */
public class BaseController {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected Claims claims;
    protected String companyId;
    protected String companyName;


    @ModelAttribute//在进入控制器Controller的方法之前执行
    public void setReqAndResp(HttpServletRequest request, HttpServletResponse response)
    {
        this.request = request;
        this.response = response;

        Object obj=request.getAttribute("user_claims");
        if(obj != null){
            this.claims = (Claims) obj;
            this.companyId= (String) claims.get("companyId");
            this.companyName= (String) claims.get("companyName");
        }
    }
    //企业id，（暂时使用1,以后会动态获取）
    public String parseCompanyId() {
        return "1";
    }
    public String parseCompanyName() {
        return "上海环达电脑有限公司";
    }
}