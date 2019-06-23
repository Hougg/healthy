package com.houxy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.houxy.constant.MessageConstant;
import com.houxy.constant.RedisMessageConstant;
import com.houxy.entity.Result;
import com.houxy.pojo.Member;
import com.houxy.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/18
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    JedisPool jedisPool;

    @Reference
    MemberService memberService;

    @RequestMapping("/check")
    public Result check(@RequestBody Map<String,String> map){
        //获取用户输入的验证码以及手机号
        String validateCode = map.get("validateCode");
        String telephone = map.get("telephone");
        //通过获取的手机号码,获取redis中的验证码
        String redisValidateCode = jedisPool.getResource().get(RedisMessageConstant.SENDTYPE_ORDER + "-" + telephone);
        //判断验证码是否一致
        if (redisValidateCode==null&&!validateCode.equals(redisValidateCode)){
            //验证码输入错误
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }else {
            //验证码输入正确
            //判断当前用户是否为会员
            return new Result(true, MessageConstant.LOGIN_SUCCESS);


        }

    }
}
