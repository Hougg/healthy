package com.houxy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.houxy.constant.MessageConstant;
import com.houxy.constant.RedisMessageConstant;
import com.houxy.entity.Result;
import com.houxy.pojo.Member;
import com.houxy.service.MemberService;
import com.houxy.utils.SMSUtils;
import com.houxy.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/16
 * @description ：
 * @version: 1.0
 */


@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    JedisPool jedisPool;

    @Reference
    MemberService memberService;

    /**
     * 发送验证码
     * 1.生成验证码
     * 2.发送验证码到手机号
     * 3.发送验证码成功,把验证码存储到redis
     * @param telephone
     * @return
     */
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        //生成验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        //发送验证码到手机号
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,String.valueOf(validateCode));
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //发送验证码成功,把验证码存储到redis
        //验证码需要在5分钟之内有效
        jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_ORDER+"-"+telephone,5*60,String.valueOf(validateCode));
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    @RequestMapping("/send4Login")
    public Result send4Login(HttpServletResponse response,String telephone){
        //生成验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        //发送短信
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //判断是否是会员,如果不是,自动注册
        //根据手机查询会员信息,如果有信息,说明已注册,没有信息,自动注册
        Member member = memberService.findByTelephone(telephone);
        if (member ==null){
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberService.reg(member);
        }
        //将生成的验证码存入到redis中
        jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_ORDER+"-"+telephone,5*60,String.valueOf(validateCode));
        //存储cookies
        //创建cookie
        Cookie cookie = new Cookie("healthy_login_telephone", telephone);
        //设置cookie的获取范围
        cookie.setPath("/");
        //设置生存时间
        cookie.setMaxAge(60*60*24*30);
        response.addCookie(cookie);
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);

    }

}
