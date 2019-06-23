package com.houxy.dao;

import com.houxy.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/13
 * @description ：
 * @version: 1.0
 */
public interface OrderSettingDao {
    OrderSetting findByOrderDate(Date orderDate);

    void edit(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(Map map);
}
