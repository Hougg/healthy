package com.houxy.service;

import com.houxy.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/13
 * @description ：
 * @version: 1.0
 */
public interface OrderSettingService {
    void addOrderSettings(List<OrderSetting> orderSettingList);

    List<Map> getOrderSettingByMonth(String month);
}
