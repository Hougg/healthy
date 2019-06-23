package com.houxy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.houxy.constant.MessageConstant;
import com.houxy.entity.Result;
import com.houxy.service.MemberService;
import com.houxy.service.ReportService;
import com.houxy.service.SetMealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/21
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    MemberService memberService;

    @Reference
    SetMealService setMealService;

    @Reference
    ReportService reportService;
    /**
     * 统计会员数量
     * @return
     */
    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        Map<String,Object> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        //向前推12个月
        calendar.add(Calendar.MONTH,-12);
        List<String> months = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            String format = sdf.format(calendar.getTime());
            months.add(format);
            calendar.add(Calendar.MONTH,1);
        }
        //把需要展示数据的月份添加到map中
        map.put("months",months);
        List<Integer> memberCount = memberService.getReportMemberCount(months);
        map.put("memberCount",memberCount);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }


    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){
        try {
            List<Map<String,String>> setmealCount = setMealService.findSetmealCount();

            Map<String,Object> map = new HashMap<>();
            //把所有的套餐名称存储到一个list集合中
            List<String> setmealNames = new ArrayList<>();
            for (Map<String, String> setmeal : setmealCount) {
                String name = setmeal.get("name");
                setmealNames.add(name);
            }

            map.put("setmealCount",setmealCount);
            map.put("setmealNames",setmealNames);

            return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }

    }

    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        try {
            Map<String,Object> result = reportService.getBusinessReportData();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }
}
