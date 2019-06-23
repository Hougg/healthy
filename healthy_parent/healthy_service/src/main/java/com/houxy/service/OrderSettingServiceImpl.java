package com.houxy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.houxy.dao.OrderSettingDao;
import com.houxy.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/13
 * @description ：
 * @version: 1.0
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    OrderSettingDao orderSettingDao;

    /**
     * 遍历集合添加预约设置对象
     * 查询该日期是否已经预约,如果已经设置预约,执行更新操作,如果没有设置预约执行添加操作
     * 注意:修改操作时,可预约人数必须大于已预约人数
     * @param orderSettingList
     */
    @Override
    public void addOrderSettings(List<OrderSetting> orderSettingList) {
        //遍历集合添加预约设置对象
        if (orderSettingList!=null){
                for (OrderSetting orderSetting : orderSettingList) {
                    //查询该日期是否已经预约
                    OrderSetting orderSettingDB = findByOrderDate(orderSetting.getOrderDate());
                    //如果已经设置预约,执行更新操作,如果没有设置预约执行添加操作
                    if (orderSettingDB!=null){
                        //修改操作时，可预约人数必须大于已预约人数
                        if (orderSetting.getNumber()>=orderSettingDB.getReservations()){
                            edit(orderSetting);
                        }else{
                            throw new RuntimeException("可预约人数必须大于等于已预约人数");
                        }
                    }else{
                        add(orderSetting);
                    }
                }
            }

    }

    @Override
    public List<Map> getOrderSettingByMonth(String month) {
        String dateBegin = month+"-1";
        String dateEnd = month+"-31";
        Map map = new HashMap();
        map.put("dateBegin",dateBegin);
        map.put("dateEnd",dateEnd);
        List<OrderSetting> orderSettingList = orderSettingDao.getOrderSettingByMonth(map);
        List<Map> leftobj = new ArrayList<>();
        for (OrderSetting orderSetting : orderSettingList) {
            Map<String,Object> orderSettingMap = new HashMap<>();
            orderSettingMap.put("date",orderSetting.getOrderDate().getDate());
            orderSettingMap.put("number",orderSetting.getNumber());
            orderSettingMap.put("reservations",orderSetting.getReservations());
            leftobj.add(orderSettingMap);

        }
        return leftobj;
    }

    public OrderSetting findByOrderDate(Date orderDate){
        return orderSettingDao.findByOrderDate(orderDate);
    }
    public void edit(OrderSetting orderSetting){
        orderSettingDao.edit(orderSetting);
    }

    public void add(OrderSetting orderSetting){
        orderSettingDao.add(orderSetting);
    }


}
