package com.houxy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.houxy.constant.MessageConstant;
import com.houxy.constant.RedisMessageConstant;
import com.houxy.entity.Result;
import com.houxy.pojo.Order;
import com.houxy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/16
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    JedisPool jedisPool;

    @Reference
    OrderService orderService;

    /**
     * 步骤:
     * 1.获取用户输入的验证码
     * 2.获取手机号码
     * 3.从redis获取验证码
     * 4.比较验证码,不相同,return error
     *              相同,开始预约
     * @param map
     * @return
     */
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map<String,Object> map){
        //获取用户输入的验证码
        Object validateCodeObj = map.get("validateCode");
        String validateCode = String.valueOf(validateCodeObj);
        //获取手机号码
        Object telephoneObj = map.get("telephone");
        String telephone = String.valueOf(telephoneObj);
        //从redis中获取验证码
        String redisValidate = jedisPool.getResource().get(RedisMessageConstant.SENDTYPE_ORDER + "-" + telephone);
        if (redisValidate == null||!redisValidate.equals(validateCode)){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }else{
            //添加预约方式为微信预约
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            Result result = orderService.addOrder(map);
            return result;
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Map<String,Object> map = orderService.findById(id);
            System.out.println("map     "+ map);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }

    }



}
