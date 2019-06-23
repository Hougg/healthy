package com.houxy.controller;

import com.houxy.constant.MessageConstant;
import com.houxy.entity.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/21
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/getUsername")
    public Result getUsername(HttpServletRequest request){
//在安全框架下,用户登录成功后,将信息存储到session域中

        //安全框架获取username的流程
        //获取安全框架的上下文对象
        SecurityContext securityContext = SecurityContextHolder.getContext();
        //获取认证对象
        Authentication authentication = securityContext.getAuthentication();
        //principle:重要信息(User)安全框架的user对象
        Object principal = authentication.getPrincipal();
        //强转为User
        User user = (User) principal;
        //获取Username
        String username = user.getUsername();
        return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,username);
    }
}
