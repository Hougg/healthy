package com.houxy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.houxy.constant.MessageConstant;
import com.houxy.entity.Result;
import com.houxy.pojo.OrderSetting;
import com.houxy.service.OrderSettingService;
import com.houxy.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/13
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(@RequestBody MultipartFile excelFile){
        try {
            //解析Excel,获取预约设置信息
            List<String[]> strList = POIUtils.readExcel(excelFile);
            //把预约设置的信息写入到数据
            List<OrderSetting> orderSettingList = new ArrayList<>();
            //把解析获取的数据,存入到List<OrderSetting>集合中
            for (String[] strs : strList) {
                //一个string数据 对应一个ordersetting
                OrderSetting orderSetting = new OrderSetting();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                orderSetting.setOrderDate(sdf.parse(strs[0]));
                orderSetting.setNumber(Integer.parseInt(strs[1]));
                //把预约设置对象添加到集合中
                orderSettingList.add(orderSetting);
                }
                //把预约设置对象添加到数据库
                orderSettingService.addOrderSettings(orderSettingList);
                //返回页面
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    /**
     * 根据日期查询预约设置
     * @param month
     * @return
     */
    @RequestMapping("/findByMonth")
    public Result findByMonth(String month){
        try {
            List<Map> orderSettingList = orderSettingService.getOrderSettingByMonth(month);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,orderSettingList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody OrderSetting orderSetting){
        try {
            List<OrderSetting> orderSettingList = new ArrayList();
            orderSettingList.add(orderSetting);
            orderSettingService.addOrderSettings(orderSettingList);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }

}
