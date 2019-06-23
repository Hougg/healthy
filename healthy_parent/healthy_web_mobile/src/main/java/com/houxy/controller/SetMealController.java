package com.houxy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.houxy.constant.MessageConstant;
import com.houxy.entity.Result;
import com.houxy.pojo.Setmeal;
import com.houxy.service.SetMealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/15
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference
    SetMealService setMealService;

    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        System.out.println(setMealService);
        try {
            List<Setmeal> setmealList = setMealService.findAll();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmealList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Setmeal setmeal = setMealService.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }

    }
}
